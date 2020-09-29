package com.example.retrofitrxjava.common.model;

import com.example.retrofitrxjava.model.ModelResponse;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Data extends ModelResponse {
    private String year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return year.equals(data.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year);
    }
}
