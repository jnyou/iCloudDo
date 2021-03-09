package io.jnyou.model;

import io.jnyou.domain.User;
import io.jnyou.domain.UserAuthAuditRecord;
import io.jnyou.domain.UserAuthInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * UseAuthInfoVo
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UseAuthInfoVo {

    private User user;

    private List<UserAuthInfo> userAuthInfoList;

    private List<UserAuthAuditRecord> userAuthAuditRecordList;

}