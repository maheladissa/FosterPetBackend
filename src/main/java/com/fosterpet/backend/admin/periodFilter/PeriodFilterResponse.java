package com.fosterpet.backend.admin.periodFilter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodFilterResponse {
    private Long userCount;
    private Long petCount;
    private Long kennelCount;
    private Long bookingCount;
}
