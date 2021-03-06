package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRequest extends RequestData{

    @ApiModelProperty("站点id")
    private String siteId;//站点id

    private String pageId;//页面id

    private String pageName;//页面名称

    private String pageAliase;//别名

    private String templateId;//模板id
}
