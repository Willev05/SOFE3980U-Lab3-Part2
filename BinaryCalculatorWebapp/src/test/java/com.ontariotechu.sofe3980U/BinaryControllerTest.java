package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter1() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }

    //Test missing operand
    @Test
    public void postParameter2() throws Exception {
        this.mvc.perform(post("/").param("operand1", "11").param("operator", "+"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "11"))
            .andExpect(model().attribute("operand1", "11"));
    }

    //Test other missing operand
    @Test
    public void postParameter3() throws Exception {
        this.mvc.perform(post("/").param("operand2", "1100").param("operator", "+"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1100"))
            .andExpect(model().attribute("operand2", "1100"));
    }

    //Test missing/invalid operator
    @Test
    public void postParameter4() throws Exception {
        this.mvc.perform(post("/").param("operand2", "1100").param("operator", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("error"));
    }

    //Test normal or.
    @Test
    public void postOr1() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "|").param("operand2", "1001"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1101"))
            .andExpect(model().attribute("operand1", "1100"))
            .andExpect(model().attribute("operand2", "1001"));
    }

    //Test one missing or operand.
    @Test
    public void postOr2() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "|"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1100"))
            .andExpect(model().attribute("operand1", "1100"));
    }

    //Test normal and.
    @Test
    public void postAnd1() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "&").param("operand2", "1001"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1000"))
            .andExpect(model().attribute("operand1", "1100"))
            .andExpect(model().attribute("operand2", "1001"));
    }

    //Test one missing and operand.
    @Test
    public void postAnd2() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "&"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"))
            .andExpect(model().attribute("operand1", "1100"));
    }

    //Test normal multiply.
    @Test
    public void postMultiply1() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "*").param("operand2", "1001"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "1101100"))
            .andExpect(model().attribute("operand1", "1100"))
            .andExpect(model().attribute("operand2", "1001"));
    }

    //Test one missing multiply operand.
    @Test
    public void postMultiply2() throws Exception {
        this.mvc.perform(post("/").param("operand1", "1100").param("operator", "*"))
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
            .andExpect(model().attribute("result", "0"))
            .andExpect(model().attribute("operand1", "1100"));
    }
}