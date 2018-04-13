package com.choxsu.elastic.config;

/**
 * @author choxsu
 */
//@Configuration
public class JFinalTxConfig {

//    @Bean
    public JFinalTxAop jFinalTxAop() {
        return new JFinalTxAop();
    }
}
