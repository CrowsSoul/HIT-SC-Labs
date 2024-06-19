/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 *
 * @param <L> The type of the vertex labels.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices) = 所有顶点的出边集的集合（出边集集族）
    // Representation invariant:
    //   所有边的权重不能为负
    //   不能有重复标签的顶点
    //   vertices中取任意一条边，它的两个顶点必须全部包含在vertice的label中
    // Safety from rep exposure:
    //   所有字段为private final
    //   所有需要获取可变数据类型的信息的方法均使用防御式拷贝
    //   Vertex是可变类型，但该类型已经经过防御式拷贝处理

    // constructor
    //由于所有字段使用final且已被初始化，所以使用默认的构造方法
    //对图的构建使用提供的add()和set()方法即可
    
    // checkRep
    private void checkRep() {
        Set<L> labels = new HashSet<>();

        for (Vertex<L> v : vertices) {
            // 检查顶点标签是否唯一
            assert !labels.contains(v.getLabel());
            labels.add(v.getLabel());

            // 检查顶点的出边集合是否符合要求
            for (L target : v.getTargets()) {
                // 检查边的权重是否大于 0
                assert v.getWeight(target) > 0;
            }
        }


        for (Vertex<L> v : vertices) {

            // 检查出边集的顶点是否被包含在vertice中
            for (L target : v.getTargets()) {
                assert labels.contains(target);
            }
        }
    }

    @Override
    public boolean add(L vertex) {
        if (!this.vertices().contains(vertex)) {
            vertices.add(new Vertex<>(vertex));
            checkRep();
            return true;
        } else {
            checkRep();
            return false;
        }
    }

    @Override
    public int set(L source, L target, int weight) {

        // 首先找到起点为source的出边集
        Vertex<L> v = null;
        for (Vertex<L> p : vertices) {
            if (p.getLabel().equals(source)) {
                v = p;
            }
        }
        // 如果v仍为null，说明不存在该边
        if (v == null) {
            // 此时若weight不为0，需要加入
            if (weight != 0) {

                // 此时仍需判断target是否在其中，若不在其中，也需要加入
                if (!this.vertices().contains(target)) {
                    Vertex<L> t = new Vertex<>(target);
                    // 防御式拷贝
                    this.vertices.add(new Vertex<>(t));
                }
                v = new Vertex<>(source);
                v.addEdge(target, weight);
                // 防御式拷贝
                this.vertices.add(new Vertex<>(v));

            }
            // 若weight为0，则不做处理
            else {

            }
            checkRep();
            return 0;
        }
        // 否则说明至少有该起点
        else {
            // 首先需要判断出边是否在其中
            // 如果有，说明target应该也在其中
            if (v.hasEdge(target)) {
                // 此时判断weight
                // 如果weight为0，则为删除操作
                if (weight == 0) {
                    int past_weight = v.removeEdge(target);
                    checkRep();
                    return past_weight;
                }
                // 如果weight不为0，则为修改操作
                else {
                    int past_weight = v.setEdge(target, weight);
                    checkRep();
                    return past_weight;
                }
            }
            // 如果没有
            // 那么target可能在其中，也可能不在其中
            else {
                // 若weight为0，则根本不需要进行删除操作。
                if (weight == 0) {
                    checkRep();
                    return 0;
                }
                // 否则需要添加该边
                else {
                    // 然后判断是否有target点
                    Vertex<L> t = null;
                    for (Vertex<L> p : vertices) {
                        if (p.getLabel().equals(target)) {
                            t = p;
                        }
                    }
                    // 没有target点
                    if (t == null) {
                        t = new Vertex<>(target);
                        this.vertices.add(new Vertex<>(t));
                    }
                    // 最后对v添加该出边
                    v.addEdge(target, weight);

                    checkRep();
                    // 因为原本没有该边，返回0
                    return 0;
                }
            }
        }

    }

    @Override
    public boolean remove(L vertex) {
        // 首先检查是否存在该顶点
        Vertex<L> v = null;
        for (Vertex<L> p : vertices) {
            if (p.getLabel().equals(vertex)) {
                v = p;
            }
            // 遍历过程中可以删除所有该顶点的入边
            p.removeEdge(vertex);
        }

        // 如果没有该顶点，返回false
        if (v == null) {
            checkRep();
            return false;
        }
        // 否则需要删除所有关联边
        else {
            // 再删除所有出边即可
            vertices.remove(v);
            checkRep();
            return true;
        }
    }

    @Override
    public Set<L> vertices() {
        // 所有的顶点标签构成的集合
        Set<L> labels = new HashSet<>();
        for (Vertex<L> v : vertices) {
            labels.add(v.getLabel());
        }
        checkRep();
        return labels;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sources = new HashMap<>();
        int weight = 0;
        // 利用Vertex中定义的方法直接遍历一遍即可
        for (Vertex<L> v : vertices) {
            weight = v.getWeight(target);
            if (weight != 0) {
                sources.put(v.getLabel(), weight);
            }
        }
        return sources;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        for (Vertex<L> v : vertices) {
            if (v.getLabel().equals(source)) {
                checkRep();
                // 已经在getMap()中进行了防御性拷贝
                return v.getMap();
            }
            // 未找到则继续查找
        }
        checkRep();
        return new HashMap<>();
    }

    // toString()
    @Override
    public String toString() {
        // 使用Vertex的toString方法来完成
        StringBuilder sb = new StringBuilder();
        for (Vertex<L> v : vertices) {
            sb.append(v.toString());
        }
        return sb.toString();
    }
}

/**
 * specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 该类表示某个顶点的所有出边的集合
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {

    // fields
    private L label;
    private Map<L, Integer> outgoingEdges;

    // Abstraction function:
    //   AF(label,outgoingEdges) = 	标签为label,
    //								且所有的出边为outgoingEdges的集合
    //
    // Representation invariant:
    //   所有边的权重为正整数
    // Safety from rep exposure:
    //   该类为可变类型，但字段label为String，不可变
    //   outgoingEdges的信息获取均使用防御式拷贝

    // constructor
    /**
     * Vertex对象的构造方法
     *
     * @param l 该顶点的标签名，String类型
     * @param o 出边集合，Key为终点，Value为出边的权重（是正整数）
     */
    public Vertex(L l, Map<L, Integer> o) {
        this.label = l;
        // 防御式拷贝
        this.outgoingEdges = new HashMap<>(o);
        checkRep();
    }

    /**
     * Vertex对象构造方法(无出边)
     * 返回一个不含出边的Vertex对象
     */
    public Vertex(L l) {
        this.label = l;
        this.outgoingEdges = new HashMap<>();
        checkRep();
    }

    /**
     * Vertex对象构造方法(副本)
     *
     * @param that 另一个Vertex对象
     *             返回一个与that值完全相同的Vertex对象
     */
    public Vertex(Vertex<L> that) {
        this.label = that.getLabel();
        // getMap()中已经使用了防御式拷贝
        this.outgoingEdges = that.getMap();
    }

    // checkRep
    private void checkRep() {
        for (Integer weight : outgoingEdges.values()) {
            assert weight > 0;
        }
    }

    // methods
    /**
     * 获取该顶点的所有目标顶点
     *
     * @return 目标顶点集合
     */
    public Set<L> getTargets() {
        checkRep();
        // 防御式拷贝
        return new HashSet<>(outgoingEdges.keySet());
    }

    /**
     * 获取该顶点到目标顶点的边的权重
     * 如果没有目标顶点，则返回0
     *
     * @param target 目标顶点
     * @return 边的权重
     */
    public int getWeight(L target) {
        if (outgoingEdges.get(target) == null) {
            checkRep();
            return 0;
        } else {
            checkRep();
            return outgoingEdges.get(target);
        }
    }

    /**
     * 获取该顶点的标签
     *
     * @return 该顶点的标签
     */
    public L getLabel() {
        checkRep();
        return label;
    }

    /**
     * 获取出边集
     *
     * @return 返回该对象的出边集
     */
    public Map<L, Integer> getMap() {
        // 防御式拷贝
        return new HashMap<>(this.outgoingEdges);
    }

    /**
     * 判断出边是否存在，不考虑权重
     *
     * @param target 出边的终点
     * @return 如果存在，返回true，否则返回false
     */
    public boolean hasEdge(L target) {
        checkRep();
        return outgoingEdges.keySet().contains(target);
    }

    /**
     * 添加一条出边,若该边存在，不修改
     *
     * @param target 出边的终点，字符串类型
     * @param weight 出边的权重，正整数
     */
    public void addEdge(L target, Integer weight) {
        if (!this.hasEdge(target)) {
            outgoingEdges.put(target, weight);
        }
        checkRep();
    }

    /**
     * 修改一条出边，若该边不存在，不修改
     *
     * @param target 出边的终点
     * @param weight 需要修改的值
     * @return 如果成功修改，则返回原权重，否则返回0
     */
    public int setEdge(L target, Integer weight) {
        int past_weight = 0;
        if (this.hasEdge(target)) {
            past_weight = outgoingEdges.get(target);
            outgoingEdges.put(target, weight);
        }
        checkRep();
        return past_weight;
    }

    /**
     * 删除一条出边，若该边不存在，则返回0，否则返回原权值
     *
     * @param target 出边的终点
     * @return past_weight 原权值，该边不存在时返回0
     */
    public int removeEdge(L target) {
        int past_weight = 0;
        if (this.hasEdge(target)) {
            past_weight = outgoingEdges.get(target);
            outgoingEdges.remove(target);
        }
        checkRep();
        return past_weight;
    }

    // toString()
    /**
     * 用字符串形式来表示Vertex类
     * 其格式为：
     * 顶点：该顶点的标签label
     * --->target1:weight1
     * --->target2:weight2
     * ...
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("顶点：" + label + "\n");
        for (Map.Entry<L, Integer> entry : outgoingEdges.entrySet()) {
            L key = entry.getKey();
            String value = entry.getValue().toString();
            sb.append("--->" + key + ":" + value + "\n");
        }
        return sb.toString();
    }
}
