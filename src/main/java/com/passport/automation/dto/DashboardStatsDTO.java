package com.passport.automation.dto;

public class DashboardStatsDTO {
    private long totalApplications;
    private long submittedCount;
    private long underVerificationCount;
    private long approvedCount;
    private long rejectedCount;

    public DashboardStatsDTO() {}

    public DashboardStatsDTO(long totalApplications, long submittedCount,
            long underVerificationCount, long approvedCount, long rejectedCount) {
        this.totalApplications = totalApplications;
        this.submittedCount = submittedCount;
        this.underVerificationCount = underVerificationCount;
        this.approvedCount = approvedCount;
        this.rejectedCount = rejectedCount;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getSubmittedCount() {
        return submittedCount;
    }

    public void setSubmittedCount(long submittedCount) {
        this.submittedCount = submittedCount;
    }

    public long getUnderVerificationCount() {
        return underVerificationCount;
    }

    public void setUnderVerificationCount(long underVerificationCount) {
        this.underVerificationCount = underVerificationCount;
    }

    public long getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(long approvedCount) {
        this.approvedCount = approvedCount;
    }

    public long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }
}
