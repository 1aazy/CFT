package com.max.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.max.api.v1.exceptions.RangeTypeMismatchException;
import com.max.api.v1.models.DigitRange;
import com.max.api.v1.models.LetterRange;
import com.max.api.v1.services.DigitRangeService;
import com.max.api.v1.services.LetterRangeService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RangeController.class)
class RangeControllerTest {

    @MockBean
    private DigitRangeService digitRangeService;

    @MockBean
    private LetterRangeService letterRangeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMinimumRange_KindDigits_returnsMinimumRange() throws Exception {

        int expectedMinStart = 1;
        int expectedMinEnd = 7;

        String expectedResult = new ObjectMapper().writeValueAsString(List.of(expectedMinStart, expectedMinEnd));

        DigitRange expectedMinRange = new DigitRange(expectedMinStart, expectedMinEnd);

        when(digitRangeService.getMinimumDigitRange()).thenReturn(expectedMinRange);

        MvcResult result = mockMvc.perform(get("/api/v1/intervals/min?kind=digits"))
                .andExpect(status().isOk())
                .andReturn();

        verify(digitRangeService, timeout(1)).getMinimumDigitRange();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(expectedResult, responseBody);
    }

    @Test
    void getMinimumRange_KindLetters_returnsMinimumRange() throws Exception {

        String expectedMinStart = "a";
        String expectedMinEnd = "f";

        String expectedResult = new ObjectMapper().writeValueAsString(List.of(expectedMinStart, expectedMinEnd));

        LetterRange expectedMinRange = new LetterRange(expectedMinStart, expectedMinEnd);

        when(letterRangeService.getMinimumLetterRange()).thenReturn(expectedMinRange);

        MvcResult result = mockMvc.perform(get("/api/v1/intervals/min?kind=letters"))
                .andExpect(status().isOk())
                .andReturn();

        verify(letterRangeService, timeout(1)).getMinimumLetterRange();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(expectedResult, responseBody);
    }

    @Test
    void getMinimumRange_KindMismatch_throwsRangeTypeMismatchException() throws Exception {

        mockMvc.perform(get("/api/v1/intervals/min?kind=error"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(RangeTypeMismatchException.class, result.getResolvedException()));
    }


    @Test
    void mergeRanges_NonExistentKind_NotFound() throws Exception {

        List<Integer[]> inputList = List.of();

        String rangesJson = objectMapper.writeValueAsString(inputList);

        mockMvc.perform(post("/api/v1/intervals/merge?kind=error")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rangesJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void mergeRanges_KindDigits_CorrectWork() throws Exception {

        List<Integer[]> inputList = List.of(
                new Integer[]{1, 4},
                new Integer[]{3, 6},
                new Integer[]{6, 7},
                new Integer[]{8, 10},
                new Integer[]{11, 14},
                new Integer[]{13, 15}
        );

        String rangesJson = objectMapper.writeValueAsString(inputList);

        mockMvc.perform(post("/api/v1/intervals/merge?kind=digits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rangesJson))
                .andExpect(status().isOk());
    }

    @Test
    void mergeRanges_KindDigitsPassEmptyList_ThrowsRangeTypeMismatchException() throws Exception {

        List<Integer[]> inputList = List.of();

        String rangesJson = objectMapper.writeValueAsString(inputList);

        mockMvc.perform(post("/api/v1/intervals/merge?kind=digits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rangesJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(RangeTypeMismatchException.class, result.getResolvedException()));
    }

    @Test
    void mergeRanges_KindLetters_CorrectWork() throws Exception {

        List<String[]> inputList = List.of(
                new String[]{"a", "f"},
                new String[]{"d", "j"},
                new String[]{"r", "z"}
        );

        String rangesJson = objectMapper.writeValueAsString(inputList);

        mockMvc.perform(post("/api/v1/intervals/merge?kind=letters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rangesJson))
                .andExpect(status().isOk());
    }

    @Test
    void mergeRanges_KindLettersPassEmptyList_ThrowsRangeTypeMismatchException() throws Exception {

        List<String[]> inputList = List.of();

        String rangesJson = objectMapper.writeValueAsString(inputList);

        mockMvc.perform(post("/api/v1/intervals/merge?kind=letters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(rangesJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(RangeTypeMismatchException.class, result.getResolvedException()));
    }
}