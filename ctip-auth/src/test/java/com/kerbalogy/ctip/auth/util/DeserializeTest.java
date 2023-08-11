package com.kerbalogy.ctip.auth.util;

import com.kerbalogy.ctip.auth.security.entity.SecurityUserDetails;
import com.kerblogy.ctip.common.util.json.JacksonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yaozongqing@outlook.com
 * @date 2023-08-10
 * @description
 **/
@SpringBootTest
public class DeserializeTest {

    @Test
    public void test() {

        String json = "{\"user\":{\"id\":1,\"createdBy\":\"system\",\"createdDate\":\"2023-05-05 06:55:49\",\"updatedBy\":\"admin\",\"updatedDate\":\"2023-05-05 10:30:46\",\"deleted\":false,\"username\":\"admin\",\"password\":\"$2a$10$bZeV/9PQ6oqwUbo4iwp06u0qoCFhmBOcRV2cMG4UI/SHPxorpUCLK\",\"phone\":\"13800000002\",\"email\":\"admin@163.com\"},\"roleIds\":[1],\"enabled\":true,\"password\":\"$2a$10$bZeV/9PQ6oqwUbo4iwp06u0qoCFhmBOcRV2cMG4UI/SHPxorpUCLK\",\"username\":\"admin\",\"authorities\":[{\"authority\":\"1\"}],\"accountNonExpired\":true,\"credentialsNonExpired\":true,\"accountNonLocked\":true}";
        JacksonUtil.from(json, SecurityUserDetails.class);
    }

}
