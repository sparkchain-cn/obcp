package cn.obcp.user.dto;

import java.util.Objects;

public class DeptDto {
    private String orgId;

    private String name;

    private String code;

    private String leader;

    private String leaderNumber;

    private Integer type;

    private Integer status;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderNumber() {
        return leaderNumber;
    }

    public void setLeaderNumber(String leaderNumber) {
        this.leaderNumber = leaderNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptDto deptDto = (DeptDto) o;
        return Objects.equals(orgId, deptDto.orgId) &&
                Objects.equals(name, deptDto.name) &&
                Objects.equals(code, deptDto.code) &&
                Objects.equals(leader, deptDto.leader) &&
                Objects.equals(leaderNumber, deptDto.leaderNumber) &&
                Objects.equals(type, deptDto.type) &&
                Objects.equals(status, deptDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, name, code, leader, leaderNumber, type, status);
    }
}
