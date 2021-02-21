package love.simbot.example.listener;

import catcode.Neko;
import love.forte.common.ioc.annotation.Beans;
import love.forte.common.ioc.annotation.Depend;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.MessageContent;
import love.forte.simbot.api.message.MessageContentBuilder;
import love.forte.simbot.api.message.MessageContentBuilderFactory;
import love.forte.simbot.api.message.containers.GroupAccountInfo;
import love.forte.simbot.api.message.containers.GroupInfo;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.simbot.example.pojo.msg;
import org.apache.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 群消息监听的示例类。
 * 所有需要被管理的类都需要标注 {@link Beans} 注解。
 * @author ForteScarlet
 */
@Beans
public class MyGroupListen {
    private static List<msg> msgList = new LinkedList<>();
    private static Logger logger = Logger.getLogger(MyGroupListen.class);

//    private static int a = 0;
    /** log */
    //private static final Logger LOG = LoggerFactory.getLogger(MyGroupListen.class);

    @Depend
    private MessageContentBuilderFactory messageContentBuilderFactory;

    /**
     * 此监听函数代表，收到消息的时候，将消息的各种信息打印出来。
     *
     * 此处使用的是模板注解 {@link OnGroup}, 其代表监听一个群消息。
     *
     * 由于你监听的是一个群消息，因此你可以通过 {@link GroupMsg} 作为参数来接收群消息内容。
     */
    @OnGroup

    public void onGroupMsg(GroupMsg groupMsg, MsgSender sender) throws Exception{
        Path p = Paths.get("groupIds.txt");
        byte[] data = Files.readAllBytes(p);
        String groupIds = new String(data, "utf-8");

        // 打印此次消息中的 纯文本消息内容。
        // 纯文本消息中，不会包含任何特殊消息（例如图片、表情等）。

//        System.out.println(groupMsg.getText());
        // 打印此次消息中的 消息内容。
        // 消息内容会包含所有的消息内容，也包括特殊消息。特殊消息使用CAT码进行表示。
        // 需要注意的是，绝大多数情况下，getMsg() 的效率低于甚至远低于 getText()
//        System.out.println(groupMsg.getMsg());

        // 获取此次消息中的 消息主体。
        // messageContent代表消息主体，其中通过可以获得 msg, 以及特殊消息列表。
        // 特殊消息列表为 List<Neko>, 其中，Neko是CAT码的封装类型。

        MessageContent msgContent = groupMsg.getMsgContent();

        // 打印消息主体
//        System.out.println(msgContent);


        // 获取发消息的人。
        GroupAccountInfo accountInfo = groupMsg.getAccountInfo();
        // 打印发消息者的账号与昵称。
//        System.out.println(accountInfo.getAccountCode());
//        System.out.println(accountInfo.getAccountNickname());


        // 获取群信息
        GroupInfo groupInfo = groupMsg.getGroupInfo();
//        // 打印群号与名称
//        System.out.println(groupInfo.getGroupCode());
//        System.out.println(groupInfo.getGroupName());
        String groupId = groupInfo.getGroupCode();
        String msgQid = accountInfo.getAccountCode();
        String msgText = groupMsg.getMsg();

        /*if("973941719".equals(msgQid)){
            sender.SENDER.sendGroupMsg(groupMsg, "你飞机呢");
        }*/
        if("2272714716".equals(msgQid)){
            sender.SENDER.sendGroupMsg(groupMsg, "发生甚么事了");
        }

        boolean b = groupIds.contains(groupId);
        if (b == true){
            //System.out.println(msgText);
            //System.out.println(groupMsg.getMsgContent());
            // 打印消息主体中的所有图片的链接（如果有的话）
            List<Neko> imageCats = msgContent.getCats("image");
           /* System.out.println("img counts: " + imageCats.size());
            for (Neko image : imageCats) {
                //System.out.println("Img url: " + image.get("url"));
            }*/



            msg msgPojo = new msg(groupId,msgQid,msgText);
            int size = 0;
            //System.out.println(groupMsg.getText());
            msgList.add(msgPojo);
            //System.out.println(msgList);
            size = msgList.size();
            System.out.println(size);
            if (size >= 500) {
                for (int j=0;j<250;j++){
                    msgList.remove(0);
                }
            }
            //System.out.println(groupMsg.getText()+groupId);
            int a = 0;
            for (msg msg1 : msgList) {

                if (msgText.contains("image,id")){
                    if (msgText.startsWith("[") && msgText.endsWith("]")){
                        String[] str = msgText.split(",");
                        String jpgIds =  str[1];
                        if(msg1.getMsgText().contains(jpgIds)&& groupId.equals(msg1.getGroupId())){
                            if(!msgQid.equals(msg1.getMsgQid())) {
                                a = a + 1;
                            }
                        }
                    }

                }else {
                    if (msgText.equals(msg1.getMsgText()) && groupId.equals(msg1.getGroupId())){
                        if(!msgQid.equals(msg1.getMsgQid())){
                            a = a + 1;
                        }
                    }
                }
            }
            MessageContentBuilder msgBuilder = messageContentBuilderFactory.getMessageContentBuilder();

            if(msgText == null || "".equals(msgText) || imageCats.size() >1){
            }else {
                if(a == 2) {
                    MessageContentBuilder text = msgBuilder.text(msgText);
                    System.out.println("发送:"+msgText+"   群名"+groupInfo.getGroupName());
                    logger.debug("发送:"+msgText+"   群名:"+groupInfo.getGroupName());
                    sender.SENDER.sendGroupMsg(groupMsg, groupMsg.getMsgContent());
                }
            }
        }


    }
}
