package com.fosterpet.backend.admin.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private Integer activeUsers;
    private Integer activeAgents;
    private Integer ongoingFostering;
    private Integer completedFostering;
}
