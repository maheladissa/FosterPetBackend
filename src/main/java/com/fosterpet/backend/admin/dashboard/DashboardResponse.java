package com.fosterpet.backend.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private Integer activeUsers;
    private Integer activeAgents;

    private Integer totalKennels;
    private Integer totalVolunteers;

    private List<Integer> weeklyPayment;

    private Integer ongoingFostering;
    private Integer completedFostering;
    private Integer pendingFostering;
    private Integer canceledFostering;
}
