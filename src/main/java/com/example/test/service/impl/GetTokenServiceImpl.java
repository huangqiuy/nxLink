package com.example.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.test.config.CheckTypeExceptionEnum;
import com.example.test.config.ChekingTypeEnum;
import com.example.test.config.ResultCodeEnum;
import com.example.test.entity.AccessToken;
import com.example.test.entity.Message;
import com.example.test.entity.PersonnelInformations;
import com.example.test.entity.ReturnAccessTokenDTO;
import com.example.test.entity.twohrattendance.AttendanceReturnDTO;
import com.example.test.entity.twohrattendance.UseAttendanceDTO;
import com.example.test.entity.twohrtoken.HRToken;
import com.example.test.entity.twohrtoken.HrTokenData;
import com.example.test.entity.workplusreturn.Items;
import com.example.test.entity.workplusreturn.ResultInfo;
import com.example.test.entity.workplusreturn.ReturnInfo;
import com.example.test.entity.workplustoken.UserDTO;
import com.example.test.entity.workplustoken.UsersReturnDTO;
import com.example.test.service.IGetTokenService;
import com.example.test.service.IPersonnelInformationsService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author HQY
 */
@Service
public class GetTokenServiceImpl implements IGetTokenService {

    @Resource
    AccessToken accessTokenParameter;

    @Resource
    IPersonnelInformationsService iPersonnelInformationsService;

//    @Value("${nx.title.newsHttp}")
//    private String newsHttp;

    @Value("${token.corp_id}")
    private String corpId;
    @Value("${token.corp_secret}")
    private String corpSecret;

    @Value("${token.sg_corp_id}")
    private String sgCorpId;
    @Value("${token.sg_corp_secret}")
    private String sgCorpSecret;

    private String accessToken = "";
    private String twoHrToken = "";
    private long expireTime = 1L;
    HttpClient client = new HttpClient();
    /**
     * 韶关标识
     */
    private final String sGIdentification = "SG";

    /**
     * 字符串末尾为零的标识
     */
    private final String endZeroIdentification = "0";

    /**
     * 获取数据成功的中文提示
     */
    private final String getDataSuccessIdentification = "ok";

    /**
     * 获取数据成功的提示码
     */
    private final String getDataSuccessTipsNumber = "status";

    private final String getDataContentTips = "message";

    //获取errcode状态码异常
    private final Integer errCodeException = 19014;

    @Override
    public String getTokens(String ticket, String userId) {
        String twoHrAttendance = new String();
        Calendar instance = Calendar.getInstance();
        long timeInMillis = instance.getTimeInMillis();
        try {
            if (timeInMillis > expireTime || timeInMillis == expireTime) {
                Message workPlusTokenMessage = this.getWorkPlusToken();
                ReturnAccessTokenDTO returnAccessTokenPojo = (ReturnAccessTokenDTO) workPlusTokenMessage.getData();
                accessToken = returnAccessTokenPojo.getAccessToken();
                expireTime = returnAccessTokenPojo.getExpireTime();
            }

            Message message = verificationTicket(ticket, accessToken);
            Integer ticketStatus = (Integer) message.getCode();
            if (ResultCodeEnum.REQUEST_HTTP_SUCCESS.getCode().equals(ticketStatus)) {
                String username = foundUserById(userId, accessToken);

                PersonnelInformations personnelInformationPojo = new PersonnelInformations();
                if (!StringUtils.isBlank(username)) {
                    personnelInformationPojo.setName(username);
                    QueryWrapper<PersonnelInformations> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("name", username);
                    PersonnelInformations personnelInformations = iPersonnelInformationsService.getOne(queryWrapper);
                    String empNo = personnelInformations.getEmpNo();
                    //判断empNo中是否包含SG 包含就把参数换成韶关的配置
                    if (empNo.indexOf(sGIdentification) == -1) {
                        Message twoHrTokenMessage = getTwoHrToken(corpId, corpSecret);
                        HrTokenData hrTokenData = (HrTokenData) twoHrTokenMessage.getData();
                        twoHrToken = hrTokenData.getAccessToken();
                    } else {
                        Message twoHrTokenMessage = getTwoHrToken(sgCorpId, sgCorpSecret);
                        HrTokenData hrTokenData = (HrTokenData) twoHrTokenMessage.getData();
                        twoHrToken = hrTokenData.getAccessToken();
                    }
                    String personId = personnelInformations.getPersonId();
                    //获取考勤记录方法
                    twoHrAttendance = getTwoHrAttendance(personId);
                }
            } else {
                twoHrAttendance = message.getMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return twoHrAttendance;
    }

//    @Override
//    public String getNewsList() {
//        String returnJson = new String();
//        try {
//            Document doc = Jsoup.connect(newsHttp).get();
//            ReturnInfo returnInfo = new ReturnInfo();
//            returnInfo.setStatus(0);
//            returnInfo.setMessage("everything is ok");
//            ArrayList<Items> itemsList = new ArrayList<>();
//            Elements listElements = doc.select("dl[class='articlelist-liststyle21']").select("dd");
//            for (Element newsEle : listElements) {
//                Elements srcImg = newsEle.select(".pic img");
//                String imgUrl = srcImg.attr("src");
//                String majorTitle = srcImg.attr("alt");
//                Elements href = newsEle.select("div.pic");
//                String url = href.select("a[href]").attr("href");
//                Elements con = newsEle.select(".con");
//                Elements select = con.select("div[class='text textOverClm clm3']");
//                String subTitle = select.text();
//                Elements eleDate = newsEle.select(".date");
//                String year = eleDate.select("div.date_box3").text();
//                Elements date_box1 = eleDate.select("div.date_box1");
//                String monthAndDay = date_box1.text().replace(" ", "");
//                String date = new String(year + "-" + monthAndDay);
//                Items items = new Items();
//                items.setTitle(majorTitle);
//                items.setSubTitle(subTitle);
//                items.setDateTime(date);
//                items.setSource("南兴官网");
//                items.setEventType("Url");
//                items.setEventValue("https://www.nanxing.com" + url);
//                items.setIconType("Url");
//                items.setIconValue(imgUrl);
//                itemsList.add(items);
//            }
//            ResultInfo resultInfo = new ResultInfo();
//            resultInfo.setItems(itemsList);
//            returnInfo.setResult(resultInfo);
//            returnJson = JSON.toJSONString(returnInfo);
//            System.out.println(returnJson);
//        } catch (Exception e) {
//
//        }
//
//        return returnJson;
//    }


    public String getTwoHrAttendance(String personId) throws UnsupportedEncodingException {
        String returnInfoJson = "";
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        String attendanceUrl = "https://openapi.2haohr.com/api/attendance/month_overview/?access_token=" + twoHrToken;
        String thisYear = "{\"year\":" + year + ",";
        String thisMonth = "\"month\":" + month + ",";
        String thisPersonId = "\"emp_ids\":[\"" + personId + "\"]}";
        String attendanceJsonStr = thisYear + thisMonth + thisPersonId;
        //post请求
        PostMethod attendancePostMethod = new PostMethod(attendanceUrl);
        attendancePostMethod.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        StringRequestEntity attendanceJsonStrRequestEntity = new StringRequestEntity(attendanceJsonStr, "application/json", "UTF-8");
        attendancePostMethod.setRequestEntity(attendanceJsonStrRequestEntity);
        Integer attendanceStatus = null;
        try {
            attendanceStatus = client.executeMethod(attendancePostMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断、取值
        if (HttpStatus.SC_OK == attendanceStatus) {
            try {
                returnInfoJson = attendancePostMethod.getResponseBodyAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map attendanceMessage = (Map) JSON.parse(returnInfoJson);
            Integer errCode = (Integer) attendanceMessage.get("errcode");
            if (ResultCodeEnum.REQUEST_HTTP_SUCCESS.getCode().equals(errCode)) {
                returnInfoJson = encapsulationCheckData(returnInfoJson);


            } else if (errCodeException.equals(attendanceMessage.get("errcode"))) {
                returnInfoJson = returnFalseData(returnInfoJson);
            }

        }

        return returnInfoJson;
    }

    /**
     * 返回虚假数据
     */
    private String returnFalseData(String data) {
        String returnInfoJson = "";
        AttendanceReturnDTO attendanceReturnPojo = JSON.parseObject(data, AttendanceReturnDTO.class);
        if (attendanceReturnPojo != null && !("").equals(attendanceReturnPojo)) {
            List<UseAttendanceDTO> useAttendanceList = attendanceReturnPojo.getData();
            ArrayList<Items> itemsList = new ArrayList<>();
            ArrayList resultList = new ArrayList();
            if (useAttendanceList == null) {
                Integer missingCard = 0;
                resultList.add(missingCard);
                Integer lateMinuteTime = 0;
                resultList.add(lateMinuteTime);
                Integer leaveHourTime = 0;
                resultList.add(leaveHourTime);
                Integer leaveTimeOffHourTime = 0;
                resultList.add(leaveTimeOffHourTime);
                Integer totalAttendanceTime = 0;
                resultList.add(totalAttendanceTime);
            }
            ArrayList titleList = new ArrayList();
            titleList.add(0, CheckTypeExceptionEnum.MISSING_CARD.getType());
            titleList.add(1, CheckTypeExceptionEnum.LATE.getType());
            titleList.add(2, CheckTypeExceptionEnum.VACATION.getType());
            titleList.add(3, CheckTypeExceptionEnum.COMPENSATORY_LEAVE.getType());
            titleList.add(4, CheckTypeExceptionEnum.ATTENDANCE.getType());

            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setStatus(0);
            returnInfo.setMessage("everything is ok");
            for (int i = 0; i < resultList.size(); i++) {
                Items items = new Items();
                items.setTitle((String) titleList.get(i));
                items.setNumber(resultList.get(i));
                items.setShowType("number");
                itemsList.add(i, items);
            }

            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setItems(itemsList);
            returnInfo.setResult(resultInfo);

            JSONObject jsonObject = (JSONObject) JSON.toJSON(returnInfo);

            returnInfoJson = jsonObject.toString();
        }
        return returnInfoJson;
    }

    /**
     * 封装考勤数据
     */
    private String encapsulationCheckData(String data) {
        String returnInfoJson = "";
        AttendanceReturnDTO attendanceReturnPojo = JSON.parseObject(data, AttendanceReturnDTO.class);
        if (attendanceReturnPojo != null && !("").equals(attendanceReturnPojo)) {
            List<UseAttendanceDTO> useAttendanceList = attendanceReturnPojo.getData();
            ArrayList<Items> itemsList = new ArrayList<>();
            ArrayList resultList = new ArrayList();
            if (useAttendanceList.size() > 0) {
                UseAttendanceDTO useAttendancePojo = useAttendanceList.get(0);
                //缺卡
                Integer missingCard = useAttendancePojo.getMissingClockCount();
                resultList.add(missingCard);
                //迟到
                Float lateMinuteTime = useAttendancePojo.getLateMinuteCount();
                String lateMinuteHour = formatCheckData(lateMinuteTime);
                resultList.add(lateMinuteHour);

                //休假
                Float leaveHourTime = useAttendancePojo.getLeaveHourCount();
                String leaveHourDay = formatCheckData(leaveHourTime);
                resultList.add(leaveHourDay);
                //调休
                Float leaveTimeOffHourTime = useAttendancePojo.getLeaveTimeOffHour();
                String leaveTimeOffHour = formatCheckData(leaveTimeOffHourTime);
                resultList.add(leaveTimeOffHour);
                //出勤
                Float totalAttendanceTime = useAttendancePojo.getExpectedAttendHour();
                String totalAttendance = formatCheckData(totalAttendanceTime);
                Integer totalAttendanceDay = Integer.valueOf(totalAttendance) / 8;
                resultList.add(totalAttendanceDay);
            }
            //存items title
            ArrayList titleList = new ArrayList();
            titleList.add(0, ChekingTypeEnum.MISSING_CARD.getType());
            titleList.add(1, ChekingTypeEnum.LATE.getType());
            titleList.add(2, ChekingTypeEnum.VACATION.getType());
            titleList.add(3, ChekingTypeEnum.COMPENSATORY_LEAVE.getType());
            titleList.add(4, ChekingTypeEnum.ATTENDANCE.getType());

            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setStatus(0);
            returnInfo.setMessage("everything is ok");
            for (int i = 0; i < resultList.size(); i++) {
                Items items = new Items();
                items.setTitle((String) titleList.get(i));
                items.setNumber(resultList.get(i));
                items.setShowType("number");
                itemsList.add(i, items);
            }

            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setItems(itemsList);
            returnInfo.setResult(resultInfo);

            JSONObject jsonObject = (JSONObject) JSON.toJSON(returnInfo);
            returnInfoJson = jsonObject.toString();
        }
        return returnInfoJson;
    }


    /**
     * 格式化考勤数据
     *
     * @param data
     * @return
     */
    public String formatCheckData(Float data) {
        DecimalFormat lateMinuteCountDecimalFormat = new DecimalFormat("0.0");
        String lateMinuteHour = lateMinuteCountDecimalFormat.format(data);
        int lateMinuteLength = lateMinuteHour.length();
        int lateMinuteIndexOf = lateMinuteHour.indexOf(".");
        int lateMinuteLastLength = lateMinuteIndexOf + 2;
        char lateMinuteLastChar = lateMinuteHour.charAt(lateMinuteLength - 1);
        if (lateMinuteLastLength == lateMinuteLength && endZeroIdentification.equals(String.valueOf(lateMinuteLastChar))) {
            lateMinuteHour = lateMinuteHour.substring(0, lateMinuteIndexOf);
        }
        return lateMinuteHour;
    }

    /**
     * 获取workPlus的token
     *
     * @return
     * @throws IOException
     */
    public Message getWorkPlusToken() throws IOException {
        Message returnMessage = new Message();
        ReturnAccessTokenDTO returnAccessTokenPojo = new ReturnAccessTokenDTO();
        String jsonResult = "";
        String url = "https://online.nanxing.com:9010/api/v1/token";
        Object accessTokenJson = JSON.toJSON(accessTokenParameter);
        String jsonStr = "";
        if (accessTokenJson != null) {
            String accessTokenJsonToString = accessTokenJson.toString();
            jsonStr = accessTokenJsonToString;
        }

        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5000);
        client.getParams().setContentCharset("UTF-8");
        PostMethod postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        if (null != jsonStr && !"".equals(jsonStr)) {
            StringRequestEntity requestEntity = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
        } else {
            returnMessage.setMessage(ResultCodeEnum.PARAM_IS_INVALID.getMessage());
            returnMessage.setCode(ResultCodeEnum.PARAM_IS_INVALID.getCode());
            return returnMessage;
        }

        Integer status = client.executeMethod(postMethod);
        if (HttpStatus.SC_OK == status) {
            jsonResult = postMethod.getResponseBodyAsString();
            Map mapMessage = (Map) JSON.parse(jsonResult);
            if (getDataSuccessIdentification.equals(mapMessage.get(getDataContentTips))) {
                Map resultMap = (Map) mapMessage.get("result");
                JSONObject jsonObject = new JSONObject();
                jsonObject.putAll(resultMap);
                returnAccessTokenPojo = jsonObject.toJavaObject(ReturnAccessTokenDTO.class);
                returnMessage.setData(returnAccessTokenPojo);
            }
        }
        return returnMessage;
    }

    /**
     * 验证ticket
     *
     * @param ticket
     * @param accessToken
     * @return
     */
    public Message verificationTicket(String ticket, String accessToken) {
        Message message = new Message();
        Integer ticketStatus = new Integer(1);
        try {
            String ticketUrl = "https://online.nanxing.com:9010/api/v1/tickets/" + ticket + "?access_token=" + accessToken;
            GetMethod getMethod = new GetMethod(ticketUrl);
            getMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
            getMethod.getParams().setContentCharset("utf-8");
            Integer status = client.executeMethod(getMethod);
            if (HttpStatus.SC_OK == status) {
                String ticketJsonResult = getMethod.getResponseBodyAsString();
                System.out.println(ticketJsonResult);
                Map ticketMap = (Map) JSON.parse(ticketJsonResult);
                if (0 == (Integer) ticketMap.get(getDataSuccessTipsNumber)) {
                    ticketStatus = (Integer) ticketMap.get(getDataSuccessTipsNumber);
                    message.setData(ticketStatus);
                    message.setCode(ResultCodeEnum.REQUEST_HTTP_SUCCESS.getCode());
                    message.setMessage(ResultCodeEnum.REQUEST_HTTP_SUCCESS.getMessage());
                } else {
                    message.setCode((Integer) ticketMap.get(getDataSuccessTipsNumber));
                    message.setMessage((String) ticketMap.get(getDataContentTips));
                }
            } else {
                message.setMessage(ResultCodeEnum.REQUEST_HTTP_TICKET_INVALID.getMessage());
                message.setCode(ResultCodeEnum.REQUEST_HTTP_TICKET_INVALID.getCode());
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 通过userID找到用户
     *
     * @param userId
     * @param accessToken
     * @return
     */
    public String foundUserById(String userId, String accessToken) {
        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        String username = "";
        try {
            String userUrl = "https://online.nanxing.com:9010/api/v1/users?access_token=" + accessToken + "&query=" + userId + "&type=id&matching=true";
            GetMethod getUserMethod = new GetMethod(userUrl);
            getUserMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
            getUserMethod.getParams().setContentCharset("utf-8");
            Integer userStatus = client.executeMethod(getUserMethod);
            if (HttpStatus.SC_OK == userStatus) {
                String userJsonResult = getUserMethod.getResponseBodyAsString();

                Map userMap = (Map) JSON.parse(userJsonResult);
                if (userMap.get(getDataSuccessTipsNumber) != null && 0 == (Integer) userMap.get(getDataSuccessTipsNumber)) {
                    UsersReturnDTO usersReturnPojo = JSON.parseObject(userJsonResult, UsersReturnDTO.class);
                    if (usersReturnPojo != null && !("").equals(usersReturnPojo)) {
                        List<UserDTO> userList = usersReturnPojo.getResult().getUsers();
                        if (userList.size() > 0) {
                            UserDTO userPojo = userList.get(0);
                            username = userPojo.getName();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 获取2号人事部Token
     *
     * @return
     */
    public Message getTwoHrToken(String corpId, String corpSecret) throws IOException {
        Message message = new Message();
        String hrTokenUrl = "https://openapi.2haohr.com/api/home/get_token/";
        String hrJsonStr = "{\"corp_id\": \"" + corpId + "\"," + "\"corp_secret\": \"" + corpSecret + "\"}";
        PostMethod postMethod = new PostMethod(hrTokenUrl);
        postMethod.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        StringRequestEntity requestEntity = new StringRequestEntity(hrJsonStr, "application/json", "UTF-8");
        postMethod.setRequestEntity(requestEntity);
        Integer twoHrStatus = 0;
        try {
            twoHrStatus = client.executeMethod(postMethod);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (HttpStatus.SC_OK == twoHrStatus) {
            String responseBodyAsString = postMethod.getResponseBodyAsString();
            if (StringUtils.isNotBlank(responseBodyAsString)) {
                HRToken hrToken = JSON.parseObject(responseBodyAsString, HRToken.class);
                //后续定义为枚举类
                if (0 == hrToken.getErrCode()) {
                    HrTokenData tokenData = hrToken.getData();
                    message.setData(tokenData);
                }
            }
        }
        return message;
    }

}
