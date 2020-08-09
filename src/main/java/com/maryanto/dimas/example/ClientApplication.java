package com.maryanto.dimas.example;

import lombok.extern.slf4j.Slf4j;
import org.mashad.jbsbe.iso.I50Factory;
import org.mashad.jbsbe.iso.I50Message;
import org.mashad.jbsbe.iso.SimpleTransformer;
import org.mashad.jbsbe.schema.I50Utility;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ClientApplication {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ParseException {
        I50Factory<SimpleTransformer> factory = new I50Factory(SimpleTransformer.class);
        I50Utility.loadFieldSchema("iso8583_schema.yml");
        PingRequest pingRequest = PingRequest
                .builder()
                .systemFunctionCode("301")
                .stan("2")
                .transmissionDateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmmss")))
                .build();
        I50Message message = factory.newMessage(pingRequest);
        log.info("iso 8583 : {}", message.debugString());

        Socket koneksi = new Socket("localhost", 12345);
        DataOutputStream out = new DataOutputStream(koneksi.getOutputStream());

        String messageRaw = message.debugString();
        out.writeShort(messageRaw.length() + 2);
        out.writeBytes(messageRaw);
        out.flush();

        koneksi.close();
    }
}
