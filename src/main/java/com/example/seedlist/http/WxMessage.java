package com.example.seedlist.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {
 *   "touser": "UserID1|UserID2|UserID3",
 *   "toparty": "PartyID1|PartyID2",
 *   "totag": "TagID1 | TagID2",
 *   "msgtype": "text",
 *   "agentid": 1,
 *   "text": {
 *     "content": "你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。"
 *   },
 *   "safe": 0,
 *   "enable_id_trans": 0,
 *   "enable_duplicate_check": 0
 * }
 */
@NoArgsConstructor
@Data
public class WxMessage implements Serializable {
    private String touser;

    private String toparty ="";

    private String totag = "";

    private String msgtype = "text";

    private int agentid = 1000011;

    private Text text;



    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Text implements Serializable {
        private String content;
    }

    public WxMessage(List<String> userList, String content) {
        this.touser = userList.stream().collect(Collectors.joining("|"));
        this.text = new Text(content);
    }
}
