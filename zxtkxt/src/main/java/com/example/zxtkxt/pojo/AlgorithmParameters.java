package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository
public class AlgorithmParameters {
    /**
     * ID
     */
    @TableId(value = "id")
    private Integer id;
    private int antCount;
    private int iterationCount;
    private double rho;
    private double alpha;
    private double beta;
    private Date createTime;

}
