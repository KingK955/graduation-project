package com.sdjzu.graduation.project.service.impl;

import com.sdjzu.graduation.project.domain.PlatToken;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sdjzu.graduation.project.mapper.PlatTokenMapper;
import com.sdjzu.graduation.project.service.PlatTokenService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PlatTokenServiceImpl implements PlatTokenService{

    @Resource
    private PlatTokenMapper platTokenMapper;

    @Override
    public PlatToken queryByUserId(Long userId) {

        Example example = new Example(PlatToken.class);

        example.createCriteria().andEqualTo("userId", userId);

        return platTokenMapper.selectOneByExample(example);

    }

    @Override
    public boolean isToken(Long userId) {

        Example example = new Example(PlatToken.class);

        example.createCriteria().andEqualTo("userId", userId);

        List<PlatToken> tokens = platTokenMapper.selectByExample(example);

        if(tokens.size()==0){
            return false;
        }else {
            return true;
        }

    }

    @Override
    public boolean add(PlatToken platToken) {
        return platTokenMapper.insert(platToken)>0 ? true:false;
    }

    @Override
    public boolean update(PlatToken platToken) {
        return platTokenMapper.updateByPrimaryKey(platToken)>0 ? true:false;
    }

}
