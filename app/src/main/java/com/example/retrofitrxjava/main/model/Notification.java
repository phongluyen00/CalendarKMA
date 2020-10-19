package com.example.retrofitrxjava.main.model;

import com.example.retrofitrxjava.model.ModelResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by luyenphong on 10/17/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
@Getter
@AllArgsConstructor
public class Notification extends ModelResponse {
    @SerializedName("data")
    private List<Data> data;

    @Getter
    public class Data extends ModelResponse{
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("link")
        @Expose
        private String links;

        @SerializedName("text")
        @Expose
        private String text;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Objects.equals(title, data.title) &&
                    Objects.equals(url, data.url) &&
                    Objects.equals(links, data.links);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, url, links);
        }
    }
}
