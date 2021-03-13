package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.CoinWithdrawAuditRecord;
import io.jnyou.mapper.CoinWithdrawAuditRecordMapper;
import io.jnyou.service.CoinWithdrawAuditRecordService;
import org.springframework.stereotype.Service;

@Service
public class CoinWithdrawAuditRecordServiceImpl extends ServiceImpl<CoinWithdrawAuditRecordMapper, CoinWithdrawAuditRecord> implements CoinWithdrawAuditRecordService {

}

