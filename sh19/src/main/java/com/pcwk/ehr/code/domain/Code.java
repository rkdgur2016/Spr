package com.pcwk.ehr.code.domain;

import com.pcwk.ehr.cmn.DTO;

public class Code extends DTO {
	private String mstCode	;//마스터코드ID
	private String detCode	;//상세코드
	private String mstNm	;//마스터코드명
	private String detNm	;//상세코드명
	private int    seq		;//순번
	private String useYn	;//사용여부
	private String regId	;//등록자
	private String regDt	;//등록일
	private String modId	;//수정자
	private String modDt	;//수정일
	
	public Code() {}

	public Code(String mstCode, String detCode, String mstNm, String detNm, int seq, String useYn, String regId,
			String regDt, String modId, String modDt) {
		super();
		this.mstCode = mstCode;
		this.detCode = detCode;
		this.mstNm = mstNm;
		this.detNm = detNm;
		this.seq = seq;
		this.useYn = useYn;
		this.regId = regId;
		this.regDt = regDt;
		this.modId = modId;
		this.modDt = modDt;
	}

	public String getMsgCode() {
		return mstCode;
	}

	public void setMsgCode(String msgCode) {
		this.mstCode = msgCode;
	}

	public String getDetCode() {
		return detCode;
	}

	public void setDetCode(String detCode) {
		this.detCode = detCode;
	}

	public String getMstNm() {
		return mstNm;
	}

	public void setMstNm(String mstNm) {
		this.mstNm = mstNm;
	}

	public String getDetNm() {
		return detNm;
	}

	public void setDetNm(String detNm) {
		this.detNm = detNm;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	@Override
	public String toString() {
		return "Code [msgCode=" + mstCode + ", detCode=" + detCode + ", mstNm=" + mstNm + ", detNm=" + detNm + ", seq="
				+ seq + ", useYn=" + useYn + ", regId=" + regId + ", regDt=" + regDt + ", modId=" + modId + ", modDt="
				+ modDt + ", toString()=" + super.toString() + "]";
	}
	
}


