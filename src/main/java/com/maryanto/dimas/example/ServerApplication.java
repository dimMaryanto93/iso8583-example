package com.maryanto.dimas.example;

import com.solab.iso8583.IsoValue;
import com.solab.iso8583.parse.AlphaParseInfo;
import com.solab.iso8583.parse.FieldParseInfo;
import com.solab.iso8583.parse.NumericParseInfo;
import lombok.extern.slf4j.Slf4j;
import org.mashad.jbsbe.iso.I50Factory;
import org.mashad.jbsbe.iso.I50Message;
import org.mashad.jbsbe.iso.SimpleTransformer;
import org.mashad.jbsbe.schema.I50Utility;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class ServerApplication {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException, ParseException {
        I50Factory<SimpleTransformer> factory = new I50Factory(SimpleTransformer.class);

        Map<Integer, FieldParseInfo> parse800 = new LinkedHashMap<>();
        parse800.put(7, new AlphaParseInfo(10));
        parse800.put(11, new NumericParseInfo(6));
        parse800.put(70, new AlphaParseInfo(3));

        factory.setParseMap(0x800, parse800);
        I50Utility.loadFieldSchema("iso8583_schema.yml");

        int port = 12345;
        ServerSocket server = new ServerSocket(port);
        log.info("Server siap di port: {}", port);

        Socket koneksi = server.accept();

        DataInputStream in = new DataInputStream(koneksi.getInputStream());
        short length = in.readShort();
        log.info("read message length: {}", length);

        byte[] data = new byte[length - 2];
        in.readFully(data);

        I50Message requestMessage = factory.parseMessage(data, 0);

        IsoValue<String> stanValue = requestMessage.getField(11);
        IsoValue<String> systemFunctionCodeValue = requestMessage.getField(70);
        IsoValue<String> transactionDate = requestMessage.getField(7);
        PingRequest pingRequestMessage = PingRequest.builder()
                .stan(stanValue.getValue())
                .transmissionDateTime(transactionDate.getValue())
                .systemFunctionCode(systemFunctionCodeValue.getValue())
                .build();
        log.info("read message: {}", pingRequestMessage.toString());


        koneksi.close();
    }
}
