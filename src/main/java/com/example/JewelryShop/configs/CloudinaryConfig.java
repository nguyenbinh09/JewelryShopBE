package com.example.JewelryShop.configs;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "dm0zao40x");
        config.put("api_key", "549671686794119");
        config.put("api_secret", "JEohPhSJDkaorH7IUHC2auFEvpU");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
