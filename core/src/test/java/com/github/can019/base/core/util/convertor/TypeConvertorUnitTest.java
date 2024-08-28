package com.github.can019.base.core.util.convertor;

import com.github.can019.base.core.util.convertor.TypeConvertor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeConvertorUnitTest {

    @Test
    @DisplayName("hexStringToByte()의 input으로 들어온 str의 길이가 16 미만이면 StringIndexOutOfBoundsException가 발생한다")
    void hexStringToByteStringIndexOutOfBoundsExceptionInputLengthUnder16(){
        String a = "123333311111111123333123333123452";
        try{
            TypeConvertor.hexStringToByte(a);
        } catch (Exception e){
            assertThat(e).isInstanceOf(StringIndexOutOfBoundsException.class);
        }
    }

    @Test
    @DisplayName("hexStringToByte()의 input으로 들어온 str의 길이가 32 초과면 StringIndexOutOfBoundsException가 발생한다")
    void hexStringToByteStringIndexOutOfBoundsExceptionInputLengthOver32(){
        String a = "123333311";
        try{
            TypeConvertor.hexStringToByte(a);
        } catch (Exception e){
            assertThat(e).isInstanceOf(StringIndexOutOfBoundsException.class);
        }
    }

    @Test
    @DisplayName("hexStringToByte()의 input으로 들어온 str가 16진수로 치환이 불가능하면 NumberFormatException가 발생한다.")
    void hexStringToByteStringNumberFormatExceptionInputCannotCastToHexNumber(){
        String a = "1233dffdfffffffs33311";
        try{
            TypeConvertor.hexStringToByte(a);
        } catch (Exception e){
            assertThat(e).isInstanceOf(NumberFormatException.class);
        }
    }
}
