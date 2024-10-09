package com.ruoyi.system.domain.vo;
import java.util.Date;

public class QuestAnswerSheetVo {
    private int routerId;
    private String routeName;
    private String imageUrl;
    private String core;
    private Date createDate;
    private Integer sumGrade;
    private Integer total;
    private Double avgGrade;
    private Double totalAvgGrade;
    private int userId;
    private int issueId;
    private String problemType;
    private Double typeWeight;
    private String problem;
    private int answerType;
    private Integer grade;
    private String answer;

    public int getRouterId() {
        return routerId;
    }

    public void setRouterId(int routerId) {
        this.routerId = routerId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getSumGrade() {
        return sumGrade;
    }

    public void setSumGrade(Integer sumGrade) {
        this.sumGrade = sumGrade;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Double getTotalAvgGrade() {
        return totalAvgGrade;
    }

    public void setTotalAvgGrade(Double totalAvgGrade) {
        this.totalAvgGrade = totalAvgGrade;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public Double getTypeWeight() {
        return typeWeight;
    }

    public void setTypeWeight(Double typeWeight) {
        this.typeWeight = typeWeight;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestAnswerSheetVo{" +
                "routerId=" + routerId +
                ", routeName='" + routeName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", core='" + core + '\'' +
                ", createDate=" + createDate +
                ", sumGrade=" + sumGrade +
                ", total=" + total +
                ", avgGrade=" + avgGrade +
                ", totalAvgGrade=" + totalAvgGrade +
                ", userId=" + userId +
                ", issueId=" + issueId +
                ", problemType='" + problemType + '\'' +
                ", typeWeight=" + typeWeight +
                ", problem='" + problem + '\'' +
                ", answerType=" + answerType +
                ", grade=" + grade +
                ", answer='" + answer + '\'' +
                '}';
    }
}