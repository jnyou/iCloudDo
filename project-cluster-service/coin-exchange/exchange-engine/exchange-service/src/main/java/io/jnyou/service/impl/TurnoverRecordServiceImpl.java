package io.jnyou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.TurnoverRecord;
import io.jnyou.mapper.TurnoverRecordMapper;
import io.jnyou.service.TurnoverRecordService;
import org.springframework.stereotype.Service;

@Service
public class TurnoverRecordServiceImpl extends ServiceImpl<TurnoverRecordMapper, TurnoverRecord> implements TurnoverRecordService {

}
