package love.simbot.example.pojo;

public class msg {
    private String groupId;
    private String msgQid;
    private String msgText;

    public msg(String groupId, String msgQid, String msgText) {
        this.groupId = groupId;
        this.msgQid = msgQid;
        this.msgText = msgText;
    }

    public msg() {

    }

    @Override
    public String toString() {
        return "msg{" +
                "groupId='" + groupId + '\'' +
                ", msgQid='" + msgQid + '\'' +
                ", msgText='" + msgText + '\'' +
                '}';
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMsgQid() {
        return msgQid;
    }

    public void setMsgQid(String msgQid) {
        this.msgQid = msgQid;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }
}
