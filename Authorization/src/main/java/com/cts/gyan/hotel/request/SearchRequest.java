package com.cts.gyan.hotel.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
	private String roomType;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private Date fromDate;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    private Date toDate;
}
