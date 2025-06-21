package com.lawfirm.lawfirmserver.lawyer.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 *
 * 通过ins-framework-mybatis工具自动生成，表lawyerconsultation的VO对象<br/>
 * 对应表名：lawyerconsultation
 *
 */
@Data
public class LawyerConsultationVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 对应字段：id,备注：咨询记录唯一标识，自增主键 */
	private Long id;
	/** 对应字段：userId,备注：用户唯一标识（如小程序openid、APP用户ID等） */
	private Long userId;
	/** 对应字段：consultContent,备注：用户填写的咨询内容（情况说明+问题） */
	private String consultContent;
	/** 对应字段：questionType,备注：问题类型（关联律师专长表lawyerSpecialty） */
	private Long questionType;
	/** 对应字段：lawyerId,备注：匹配的律师ID（若已分配） */
	private Long lawyerId;
	/** 对应字段：lawyerReply,备注：律师回复内容 */
	private String lawyerReply;
	/** 对应字段：createTime,备注：咨询发起时间，默认为当前时间 */
	private Date createTime;
	/** 对应字段：replyTime,备注：律师回复时间 */
	private Date replyTime;
	/** 对应字段：agreeProtocol,备注：是否同意协议（0：未同意；1：已同意） */
	private String agreeProtocol;
	/** 对应字段：status,备注：咨询状态（0：待回复；1：已回复；2：已关闭；3：已取消） */
	private Integer status;
	/** 对应字段：insertTimeForHis,备注：记录插入时间，默认为当前时间 */
	private Date insertTimeForHis;
	/** 对应字段：operateTimeForHis,备注：记录操作时间，自动更新 */
	private Date operateTimeForHis;

}
