package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.CashWithdrawAuditRecord;
import io.jnyou.mapper.CashWithdrawAuditRecordMapper;
import io.jnyou.service.CashWithdrawAuditRecordService;
import org.springframework.stereotype.Service;

@Service
public class CashWithdrawAuditRecordServiceImpl extends ServiceImpl<CashWithdrawAuditRecordMapper, CashWithdrawAuditRecord> implements CashWithdrawAuditRecordService {

}

