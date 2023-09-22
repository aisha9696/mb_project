package kz.mb.project.mb_project.dto;


import java.io.Serializable;
import lombok.Data;


@Data
public class SmsResponse implements Serializable {
    private String bulk_id;
    private String message_id;
    private String extra_id;
    private String to;
    private String sender;
    private String text;
    private String sent_at;
    private String done_at;
    private Integer sms_count;
    private Integer priority;
    private String callback_data;
    private String status;
    private Integer mnc;
    private String err;

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }
}
