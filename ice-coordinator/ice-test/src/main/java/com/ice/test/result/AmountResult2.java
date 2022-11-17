package com.ice.test.result;

import com.ice.core.context.IcePack;
import com.ice.core.context.IceRoam;
import com.ice.core.leaf.pack.BaseLeafPackResult;
import com.ice.test.service.SendService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Resource;

@Data
@EqualsAndHashCode(callSuper = true)
public class AmountResult2 extends BaseLeafPackResult {
    //选择质量最高的
    @Resource
    private SendService sendService;
    //给谁发
    private String key;
    //发多少
    private Object value;


    @Override
    protected boolean doPackResult(IcePack pack) {
        IceRoam roam = pack.getRoam();
        Integer uid = roam.getMulti(key);
        if (uid == null) {
            return false;
        }
        String value = roam.getUnion(this.value);
        if (value ==null) {
            return false;
        }
        boolean res = sendService.sendAmount(uid, value);
        roam.putMulti("result." + "sendAmount", value);
        roam.putMulti("result." + "scene", pack.getScene());
        return res;
    }
}
