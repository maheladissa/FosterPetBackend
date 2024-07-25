package com.fosterpet.backend.admin;

import com.fosterpet.backend.admin.dashboard.DashboardResponse;
import com.fosterpet.backend.admin.periodFilter.PeriodFilterResponse;

public interface AdminService {

    DashboardResponse getDashboardData();

    PeriodFilterResponse getPeriodFilterData(String startDate, String endDate);

    Double getDailyRevenue(String date);

}
