package com.farpower.iot.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtocolUtil {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        BaseRequestProto.RequestProtocol protocol = BaseRequestProto.RequestProtocol.newBuilder()
                .setRequestId(123)
                .setReqMsg("select \r\n" + 
                        "a.USER_CODE as \"员工编码\",\r\n" + 
                        "a.\"NAME\" as \"姓名\",\r\n" + 
                        "a.IDENTIFICATION_CARD as \"身份证号码\",\r\n" + 
                        "e.COMPANY_ID as \"单位编码\",\r\n" + 
                        "e.COMPANY_NAME as \"单位名称\",\r\n" + 
                        "case d.organ_type\r\n" + 
                        "    when '3' then e.parent_id\r\n" + 
                        "    else \r\n" + 
                        "        d.organ_id\r\n" + 
                        "end  as \"部门代码\",\r\n" + 
                        "case d.organ_type\r\n" + 
                        "    when '3' then e.parent_name\r\n" + 
                        "    else \r\n" + 
                        "        d.organ_name\r\n" + 
                        "end  as \"部门名称\",\r\n" + 
                        "case d.organ_type\r\n" + 
                        "    when '3' then d.organ_id\r\n" + 
                        "end  as \"班组代码\",\r\n" + 
                        "case d.organ_type\r\n" + 
                        "    when '3' then d.organ_name\r\n" + 
                        "end  \"班组名称\",\r\n" + 
                        "c.POSITION_ID as \"岗位代码\",\r\n" + 
                        "c.POSITION_NAME as \"岗位名称\",\r\n" + 
                        "case b.primary_flag\r\n" + 
                        "    when '1' then '是'\r\n" + 
                        "  else '否'\r\n" + 
                        "end as \"是否主岗\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='statu' and dict_item_value=a.USER_STATUS and rownum=1 )\r\n" + 
                        "-- a.USER_STATUS \r\n" + 
                        "as  \"岗位状态\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='postLevel' and dict_item_value=a.POST_LEVEL and rownum=1 )\r\n" + 
                        "-- a.POST_LEVEL \r\n" + 
                        "as \"岗位级别\",\r\n" + 
                        "\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='jobtype' and dict_item_value=c.POSITION_NATURE and rownum=1 )\r\n" + 
                        "-- c.POSITION_NATURE \r\n" + 
                        "as \"岗位性质分类\",\r\n" + 
                        "-- a.SEX \r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='sex' and dict_item_value=a.SEX and rownum=1 )\r\n" + 
                        "as \"性别\",\r\n" + 
                        "a.BIRTHDAY as \"出生日期\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='nation' and dict_item_value=a.NATIONALITY and rownum=1 )\r\n" + 
                        "-- a.NATIONALITY \r\n" + 
                        "as \"名族\",\r\n" + 
                        "a.NATIVE_PLACE as \"籍贯\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='regist' and dict_item_value=a.HOUSEHOLD_TYPE and rownum=1 )\r\n" + 
                        "-- a.HOUSEHOLD_TYPE \r\n" + 
                        "as \"户口性质\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='english' and dict_item_value=a.LANGUAGE_LEVEL and rownum=1 )\r\n" + 
                        "-- a.LANGUAGE_LEVEL \r\n" + 
                        "as \"外语等级\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='polictic' and dict_item_value=a.POLITICAL_STATUS and rownum=1 )\r\n" + 
                        "-- a.POLITICAL_STATUS \r\n" + 
                        "as \"政治面貌\",\r\n" + 
                        "a.JOIN_PARTY_DATE as \"入党日期\",\r\n" + 
                        "null as \"直接上级员工编号\",\r\n" + 
                        "null as \"直接上级姓名\",\r\n" + 
                        "null as \"直接上级岗位名称\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='education' and dict_item_value=a.FIRST_EDUCATION and rownum=1 )\r\n" + 
                        "-- a.FIRST_EDUCATION \r\n" + 
                        "as \"第一学历\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='degree' and dict_item_value=a.FIRST_DEGREE and rownum=1 )\r\n" + 
                        "-- a.FIRST_DEGREE \r\n" + 
                        "as \"第一学位\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='study' and dict_item_value=a.FIRST_EDUCATION_STUDY_TYPE and rownum=1 )\r\n" + 
                        "-- a.FIRST_EDUCATION_STUDY_TYPE \r\n" + 
                        "as \"第一学历学习形式\",\r\n" + 
                        "a.FIRST_GRADUATE_DATE as \"第一学历毕业日期\",\r\n" + 
                        "a.FIRST_GRADUATE_SCHOOL as \"第一学历毕业院校\",\r\n" + 
                        "a.FIRST_EDUCATION_MAJOR as \"第一学历专业名称\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='education' and dict_item_value=a.HIGHEST_EDUCATION and rownum=1 )\r\n" + 
                        "-- a.HIGHEST_EDUCATION \r\n" + 
                        "as \"最高学历\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='degree' and dict_item_value=a.HIGHEST_DEGREE and rownum=1 )\r\n" + 
                        "-- a.HIGHEST_DEGREE \r\n" + 
                        "as \"最高学位\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='study' and dict_item_value=a.HIGHEST_EDUCATION_STUDY_TYPE and rownum=1 )\r\n" + 
                        "-- a.HIGHEST_EDUCATION_STUDY_TYPE \r\n" + 
                        "as \"最高学历学习形式\",\r\n" + 
                        "a.HIGHEST_GRADUATE_DATE as \"最高学历毕业日期\",\r\n" + 
                        "a.HIGHEST_GRADUATE_SCHOOL as \"最高学历毕业院校\",\r\n" + 
                        "a.HIGHEST_EDUCATION_MAJOR as \"最高学历专业名称\",\r\n" + 
                        "a.CONTACT_ADDRESS as \"联系地址\",\r\n" + 
                        "a.SECOND_CONTACT_ADDRESS as \"第二联系地址\",\r\n" + 
                        "a.POST_CODE as \"邮政编码\",\r\n" + 
                        "a.OFFICE_PHONE as \"办公电话\",\r\n" + 
                        "a.MOBILE    as \"移动电话\",\r\n" + 
                        "a.ARRIVAL_DATE as \"任职日期\",\r\n" + 
                        "a.START_WORK_DATE as \"参加工作日期\",\r\n" + 
                        "a.JOIN_DATE as \"入单位日期\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='committee' and dict_item_value=a.PARTY_PRINCIPAL_TYPE and rownum=1 )\r\n" + 
                        "-- a.PARTY_PRINCIPAL_TYPE \r\n" + 
                        "as \"党委负责人标志\",\r\n" + 
                        "a.PROFESSION_TYPE as \"职业工种\",\r\n" + 
                        "a.SPECIAL_PROFESSION as \"特殊工种\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='technical' and dict_item_value=a.PROFESSIONAL_POSITION and rownum=1 )\r\n" + 
                        "-- a.PROFESSIONAL_POSITION \r\n" + 
                        "as \"专业技术职务名称\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='technique' and dict_item_value=a.PROFESSIONAL_POSITION_SERIES and rownum=1 )\r\n" + 
                        "-- a.PROFESSIONAL_POSITION_SERIES \r\n" + 
                        "as \"专业技术职务系列\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='techniquth' and dict_item_value=a.PROFESSIONAL_POSITION_LEVEL and rownum=1 )\r\n" + 
                        "-- a.PROFESSIONAL_POSITION_LEVEL \r\n" + 
                        "as \"专业技术职务等级\",\r\n" + 
                        "a.PROFESSIONAL_GET_DATE as \"职称取得时间\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='qualification' and dict_item_value=a.PROFESSIONAL_LEVEL and rownum=1 )\r\n" + 
                        "-- a.PROFESSIONAL_LEVEL \r\n" + 
                        "as \"职业资格等级\",\r\n" + 
                        "a.PROFESSIONAL_LEVEL_DATE as \"职业资格等级取得时间\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='statu' and dict_item_value=a.USER_STATUS and rownum=1 )\r\n" + 
                        "-- a.USER_STATUS \r\n" + 
                        "as \"人员状态\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='workform' and dict_item_value=a.EMPLOY_TYPE and rownum=1 )\r\n" + 
                        "-- a.EMPLOY_TYPE \r\n" + 
                        "as \"用工形式\",\r\n" + 
                        "a.USER_IDENTITY_TYPE as \"职工原身份\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='expert' and dict_item_value=a.EXPERT_PERSON_TYPE and rownum=1 )\r\n" + 
                        "-- a.EXPERT_PERSON_TYPE \r\n" + 
                        "as \"专家人才类型\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='expertway' and dict_item_value=a.EXPERT_GET_TYPE and rownum=1 )\r\n" + 
                        "-- a.EXPERT_GET_TYPE \r\n" + 
                        "as \"专家称号获得方式\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='changetype' and dict_item_value=a.CHANGE_TYPE and rownum=1 )\r\n" + 
                        "-- a.CHANGE_TYPE \r\n" + 
                        "as \"变动类型\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='addreason' and dict_item_value=a.ADD_REASON and rownum=1 )\r\n" + 
                        "-- a.ADD_REASON \r\n" + 
                        "as \"增加原因\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='cutreason' and dict_item_value=a.CUT_TYPE and rownum=1 )\r\n" + 
                        "-- a.CUT_TYPE \r\n" + 
                        "as \"减少原因\",\r\n" + 
                        "a.CONTRACT_NO as \"劳动合同编号\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='pactype' and dict_item_value=a.CONTRACT_TYPE and rownum=1 )\r\n" + 
                        "-- a.CONTRACT_TYPE \r\n" + 
                        "as \"劳动合同类型\",\r\n" + 
                        "(select dict_item_name from T_MD_CODE_DICT_ITEM where DICT_KEY='pactdatetype' and dict_item_value=a.CONTRACT_TERM_TYPE and rownum=1 )\r\n" + 
                        "-- a.CONTRACT_TERM_TYPE \r\n" + 
                        "as \"劳动合同期限类型\",\r\n" + 
                        "null as \"劳动合同签订主体\",\r\n" + 
                        "a.CONTRACT_SIGN_DATE as \"劳动合同签订日期\",\r\n" + 
                        "a.CONTRACT_START_DATE as \"劳动合同开始日期\",\r\n" + 
                        "a.CONTRACT_END_DATE as \"劳动合同结束日期\",\r\n" + 
                        "a.REMARK as \"备注\"\r\n" + 
                        "\r\n" + 
                        "\r\n" + 
                        "\r\n" + 
                        "from T_MD_USER a \r\n" + 
                        "join T_MD_USER_POSITION b on a.USER_ID = b.USER_ID \r\n" + 
                        "join T_MD_POSITION c on b.POSITION_ID = c.POSITION_ID \r\n" + 
                        "join T_MD_ORGAN d on c.ORGAN_ID = d.ORGAN_ID \r\n" + 
                        "join V_ORG_ORGAN_REF e on d.ORGAN_ID = e.ORGAN_ID \r\n" + 
                        "where a.DELETE_FLAG_=0  and b.DELETE_FLAG_=0  and c.delete_flag_=0 and d.delete_flag_=0\r\n" + 
                        "-- left join T_MD_ORGAN f on d.parent_id = f.organ_id\r\n" + 
                        "-- left join t_md_organ g on f.parent_id = g.organ_id\r\n" + 
                        "-- left join t_md_organ h on g.parent_id = h.organ_id\r\n" + 
                        "-- left join t_md_organ i on h.parent_id = i.organ_id\r\n" + 
                        "-- WHERE rownum < 100")
                .build();

        byte[] encode = encode(protocol);

        BaseRequestProto.RequestProtocol parseFrom = decode(encode);

        System.out.println(protocol.toString());
        System.err.println(parseFrom.getReqMsg());
        
    }

    /**
     * 编码
     * @param protocol
     * @return
     */
    public static byte[] encode(BaseRequestProto.RequestProtocol protocol){
        return protocol.toByteArray() ;
    }

    /**
     * 解码
     * @param bytes
     * @return
     * @throws InvalidProtocolBufferException
     */
    public static BaseRequestProto.RequestProtocol decode(byte[] bytes) throws InvalidProtocolBufferException {
        return BaseRequestProto.RequestProtocol.parseFrom(bytes);
    }
}