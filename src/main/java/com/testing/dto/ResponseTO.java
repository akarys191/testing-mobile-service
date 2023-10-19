package com.testing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseTO<T> {
    private boolean success;
    private T data;
    private ResponseTO.ErrorTO error;

    public static <T> ResponseTO<T> success(T data) {
        return builder().success(true).data(data).build();
    }

    public static <T> ResponseTO<T> failure(String errorMessage,  int errorCode) {
        return builder().success(false).error(new ResponseTO.ErrorTO(errorMessage, errorCode)).build();
    }

    public static <T> ResponseTO<T> badError(String badErrorException) {
        return builder().success(false).error(new ResponseTO.ErrorTO(badErrorException, 500_001)).build();
    }

    public static <T> ResponseTO<T> badClientError(String errorMessage) {
        return builder().success(false).error(new ResponseTO.ErrorTO(errorMessage, 400_001)).build();
    }

    public static <T> ResponseTO<T> notFoundClientError(String errorMessage) {
        return builder().success(false).error(new ResponseTO.ErrorTO(errorMessage, 404_001)).build();
    }

    public static <T> ResponseTOBuilder builder() {
        return new ResponseTO.ResponseTOBuilder();
    }


    public static class ResponseTOBuilder<T> {
        private boolean success;
        private T data;
        private ResponseTO.ErrorTO error;

        ResponseTOBuilder() {
        }

        public ResponseTO.ResponseTOBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ResponseTO.ResponseTOBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseTO.ResponseTOBuilder<T>  error(ResponseTO.ErrorTO error) {
            this.error = error;
            return this;
        }

        public ResponseTO<T> build() {
            return new ResponseTO<T>(this.success, this.data, this.error);
        }

        public String toString() {
            return "ResponseTO.ResponseTOBuilder<T> (success=" + this.success + ", data=" + this.data + ", error=" + this.error + ")";
        }
    }

    @Getter
    @Setter
    public static class ErrorTO {
        @JsonProperty("user_msg")
        private String message;
        @JsonProperty("error_code")
        private int code;

        public String getMessage() {
            return this.message;
        }

        public int getCode() {
            return this.code;
        }

        public ErrorTO() {
        }

        public ErrorTO(String message, int code) {
            this.message = message;
            this.code = code;
        }
    }
}
