package com.ruoyi.system.domain.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.util.Date;

public class QuestAnswerSheetVo {
    /** 问卷ID */
    @Excel(name = "问卷ID")
    private int routerId;

    /** 路线名称 */
    private String routeName;

    /** 图片路径 */
    private String imageUrl;

    /** 总分 */
    private String core;


    /** 总分 */
    private Integer sumGrade;

    /** 总数 */
    private Integer total;

    /** 每份问卷平均分 */
    private Double avgGrade;

    /** 总的平均分 */
    private Double totalAvgGrade;

    /** 用户编号 */
    @Excel(name = "用户编号")
    private int userId;

    /** 问题编号 */
    @Excel(name = "问题编号")
    private int issueId;

    /** 问题类型 */
    @Excel(name = "问题类型")
    private String problemType;

    /** 问题类型权重 */
    @Excel(name = "类型权重")
    private Double typeWeight;

    /** 问题 */
    @Excel(name = "问题")
    private String problem;

    /** 回答类型 */
    @Excel(name = "回答类型" )
    private int answerType;

    /** 分数 */
    @Excel(name = "分数")
    private Integer grade;

    /** 得分 */
    @Excel(name = "得分")
    private String answer;

    /** 回答选项 */
    @Excel(name = "回答选项")
    private String answerResult;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;


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

    public String getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(String answerResult) {
        this.answerResult = answerResult;
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
                ", answerResult='" + answerResult + '\'' +
                '}';
    }
}