package dev.luisjohann.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import dev.luisjohann.vo.AjusteItemVO;
import dev.luisjohann.vo.AjusteVO;
import dev.luisjohann.vo.OperacaoItemVO;
import dev.luisjohann.vo.OperacaoVO;
import dev.luisjohann.vo.PessoaVO;
import dev.luisjohann.vo.ProdutoVO;
import dev.luisjohann.vo.UsuarioVO;

public class EstoqueCollectors {

    public final BeanCollector<Integer, UsuarioVO> usuarioCollector = new BeanCollector<>();
    public final BeanCollector<Integer, PessoaVO> pessoaCollector = new BeanCollector<>();
    public final BeanCollector<Integer, ProdutoVO> produtoCollector = new BeanCollector<>();
    public final BeanCollector<Integer, OperacaoVO> operacaoCollector = new BeanCollector<>();
    public final BeanCollector<Integer, OperacaoItemVO> operacaoItemCollector = new BeanCollector<>();
    public final BeanCollector<Integer, AjusteVO> ajusteCollector = new BeanCollector<>();
    public final BeanCollector<Integer, AjusteItemVO> ajusteItemCollector = new BeanCollector<>();

    public void postCollect() {
        // standalone queries
        this.usuarioCollector.postCollect();
        this.pessoaCollector.postCollect();
        this.produtoCollector.postCollect();
        this.operacaoCollector.postCollect();
        this.operacaoItemCollector.postCollect();
        this.ajusteCollector.postCollect();
        this.ajusteItemCollector.postCollect();

    }

    public static class BeanCollector<K, B> {
        private final Map<K, B> beanMap = new HashMap<>();
        private final Map<K, List<Consumer<B>>> beanSetterMap = new HashMap<>();
        private final List<Consumer<B>> onReadyList = new ArrayList<>();

        public Set<K> getMissingIdSet() {
            return this.beanSetterMap.keySet();
        }

        public Set<K> getIdSet() {
            Set<K> idSet = new HashSet<>();
            idSet.addAll(this.beanMap.keySet());
            idSet.addAll(this.beanSetterMap.keySet());
            return idSet;
        }

        public boolean containsKey(K id) {
            return this.beanMap.containsKey(id);
        }

        public B get(K id) {
            return this.beanMap.get(id);
        }

        public void push(K id, Consumer<B> setter) {
            B bean = this.beanMap.get(id);
            if (bean != null) {
                setter.accept(bean);
                return;
            }

            List<Consumer<B>> setterList = this.beanSetterMap.get(id);
            if (setterList == null) {
                setterList = new ArrayList<>();
                this.beanSetterMap.put(id, setterList);
            }
            setterList.add(setter);
        }

        public void onReady(Consumer<B> setter) {
            this.onReadyList.add(setter);
        }

        public void put(K key, B bean) {
            this.beanMap.put(key, bean);
            List<Consumer<B>> setterList = this.beanSetterMap.remove(key);
            if (setterList != null) {
                for (Consumer<B> setter : setterList) {
                    setter.accept(bean);
                }
            }
        }

        void postCollect() {
            for (B bean : this.beanMap.values()) {
                for (Consumer<B> setter : this.onReadyList) {
                    setter.accept(bean);
                }

                for (List<Consumer<B>> setterList : this.beanSetterMap.values()) {
                    for (Consumer<B> setter : setterList) {
                        setter.accept(bean);
                    }
                }
            }
        }
    }
}
