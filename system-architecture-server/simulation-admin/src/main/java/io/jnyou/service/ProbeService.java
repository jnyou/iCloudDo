package io.jnyou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.core.base.BaseAsset;
import io.jnyou.core.dos.ProbeDO;
import io.jnyou.core.dtos.ProbeDTO;
import io.jnyou.core.vo.AssetVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jnyou
 * @since 2020-12-21
 */
public interface ProbeService extends IService<ProbeDO> {

    BaseAsset getProbe(Integer id);

    List<ProbeDTO> getProbeInfoByDeviceIds(String[] deviceIds);

    List<AssetVo> getAssetsByParentId(Integer parentId);

    List<ProbeDTO> listProbe();

    ProbeDTO transProbeDTO(ProbeDO probeDO);

    AssetVo getProbesById(Integer probeId);

    List<String> getSceneAsset(List<String> paths, List<Integer> catalogs);

    List<Integer> selectProbeByPartent(List<Integer> devices);

    List<ProbeDO> findAll();

    List<ProbeDTO> transProbeDTOList(List<ProbeDO> probeDOS);


    /**
     * 分页获取同时包含数组中标签编码的监测器ID列表
     *
     * @param tagCodes 标签编码
     * @return
     */
    List<List<Integer>> getProbeIdsByTagCodes(List<List<String>> tagCodes);

    /**
     * 分页获取同时包含数组中标签编码的监测器ID列表
     *
     * @param tagCodes 标签编码
     * @return
     */
    List<List<Integer>> getProbeIdsByTagIds(List<List<Integer>> tagIds);
}
