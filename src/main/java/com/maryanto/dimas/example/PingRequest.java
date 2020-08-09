package com.maryanto.dimas.example;

import lombok.Builder;
import lombok.Data;
import org.mashad.jbsbe.annotation.Iso8583;
import org.mashad.jbsbe.annotation.IsoField;

@Data
@Builder
@Iso8583(type = 0x800)
public class PingRequest {

    @IsoField(index = 7)
    public String transmissionDateTime;
    @IsoField(index = 11)
    public String stan;
    @IsoField(index = 70)
    public String systemFunctionCode;

}
