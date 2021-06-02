package com.example.retrofitrxjava.main.model;

import com.example.retrofitrxjava.loginV3.model.DataResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBDTB {
    private boolean isSuccess;
    private List<DataResponse.ResponseBDTB> bangDiemTB;
}
