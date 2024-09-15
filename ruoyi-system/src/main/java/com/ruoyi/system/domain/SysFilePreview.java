package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysFilePreview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 原文件名（上传前文件名） */
    @Excel(name = "原文件名", readConverterExp = "上传前文件名")
    private String filename;

    /** 文件后缀（.xls;.xlsx;.ppt;.doc;.docx;.pptx.pdf） */
    @Excel(name = "文件后缀", readConverterExp = ".=xls;.xlsx;.ppt;.doc;.docx;.pptx;.pdf")
    private String filetype;

    /** 上传后文件路径 */
    @Excel(name = "上传后文件路径")
    private String uploadpath;

    /** 上传后文件名 */
    @Excel(name = "上传后文件名")
    private String uploadfilename;

    /** 转换pdf路径 */
    @Excel(name = "转换pdf路径")
    private String pdfpath;

    /** 转换pdf文件名 */
    @Excel(name = "转换pdf文件名")
    private String pdfname;

    /** 0(产品说明书）1（常见故障案例） */
    @Excel(name = "0(产品说明书）1", readConverterExp = "常=见故障案例")
    private String documentType;

    /** 预览地址 */
    @Excel(name = "预览地址")
    private String previewUrl;



    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getFilename()
    {
        return filename;
    }
    public void setFiletype(String filetype)
    {
        this.filetype = filetype;
    }

    public String getFiletype()
    {
        return filetype;
    }
    public void setUploadpath(String uploadpath)
    {
        this.uploadpath = uploadpath;
    }

    public String getUploadpath()
    {
        return uploadpath;
    }
    public void setUploadfilename(String uploadfilename)
    {
        this.uploadfilename = uploadfilename;
    }

    public String getUploadfilename()
    {
        return uploadfilename;
    }
    public void setPdfpath(String pdfpath)
    {
        this.pdfpath = pdfpath;
    }

    public String getPdfpath()
    {
        return pdfpath;
    }

    public void setDocumentType(String documentType)
    {
        this.documentType = documentType;
    }

    public String getDocumentType()
    {
        return documentType;
    }

    public void setPdfname(String pdfname)
    {
        this.pdfname = pdfname;
    }

    public String getPdfname()
    {
        return pdfname;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("filename", getFilename())
                .append("filetype", getFiletype())
                .append("uploadpath", getUploadpath())
                .append("uploadfilename", getUploadfilename())
                .append("pdfpath", getPdfpath())
                .append("documentType", getDocumentType())
                .append("pdfname", getPdfname())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .toString();
    }
}

