package org.jnyou.clustest;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.beanutils.BeanPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.functors.EqualPredicate;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @className GetTargetList
 * @author: JnYou xiaojian19970910@gmail.com
 **/
public class GetTargetList {

    public static void main(String[] args) {
        List<MkArea> strs = new ArrayList<>();
        MkArea mkArea = new MkArea().setName("水温监测1").setAge(20);
        MkArea mkArea1 = new MkArea().setName("有功功率").setAge(30);
        MkArea mkArea2 = new MkArea().setName("有功室内").setAge(50);
        strs.add(mkArea);
        strs.add(mkArea1);
        strs.add(mkArea2);
        List<String> searchList = null;
        List<MkArea> collect = strs.parallelStream().filter(mk -> mk.getName().contains("有功")).collect(Collectors.toList());
        System.out.println(collect);
//        for (MkArea str : strs) {
//            searchList = (List<String>) GetTargetList.checkList(strs,"name" ,"有功");
//        }
//        System.out.println(searchList);

    }

    @Data
    @Accessors(chain = true)
    static class MkArea{
        private String name;
        private Integer age;
    }


    public static Collection checkList(List list,String tableColumnName,String agers){
        List templist = new ArrayList();
        EqualPredicate parameter = new EqualPredicate(agers);
        BeanPredicate tableCoulmn_paramerter = new BeanPredicate(tableColumnName, parameter);
        Predicate[] allPredicateArray = {tableCoulmn_paramerter };
        Predicate allPredicate = PredicateUtils.allPredicate(allPredicateArray);
        Collection<T> filteredCollection = CollectionUtils.select(list, allPredicate);
        return filteredCollection;
    }


}

