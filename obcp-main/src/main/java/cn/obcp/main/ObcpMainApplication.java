package cn.obcp.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
//@EnableDiscoveryClient
@ComponentScan(basePackages="cn.obcp")
@MapperScan("cn.obcp.**.mapper")
public class ObcpMainApplication {

      public static void main(String[] args) {
    	  
    	  SpringApplication application = new SpringApplication(ObcpMainApplication.class);
          application.run(args);  
      }
}
