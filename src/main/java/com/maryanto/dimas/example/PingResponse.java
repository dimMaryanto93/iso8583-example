package com.maryanto.dimas.example;

import lombok.Builder;
import lombok.Data;
import org.mashad.jbsbe.annotation.Iso8583;
import org.mashad.jbsbe.annotation.IsoField;

@Data
@Builder
@Iso8583(type = 0x810)
public class PingResponse {

    @IsoField(index = 7)
    public String transmissionDateTime;
    @IsoField(index = 11)
    public Long stan;
    @IsoField(index = 39)
    public String responseCode;
    @IsoField(index = 70)
    public String systemFunctionCode;
}
