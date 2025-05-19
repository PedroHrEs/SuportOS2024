package com.curso.services;

import com.curso.domains.GrupoProduto;
import com.curso.domains.Produto;
import com.curso.domains.dtos.ProdutoDTO;
import com.curso.repositories.GrupoProdutoRepository;
import com.curso.repositories.ProdutoRepository;
import com.curso.services.exceptions.DataIntegrityViolationException;
import com.curso.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepo;

    @Autowired
    private GrupoProdutoRepository grupoProdutoRepo;

    public List<ProdutoDTO> findAll(){
        return produtoRepo.findAll().stream().map(obj -> new ProdutoDTO(obj)).collect(Collectors.toList());
    }
    public Produto findById(Long id){
        Optional<Produto> obj = produtoRepo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado id:"+id));
    }
    public Produto findByCodigoBarra(String codigoBarra){
        Optional<Produto> obj = produtoRepo.findByCodigoBarra(codigoBarra);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado codigo de barras:"+codigoBarra));
    }
    public Produto create(ProdutoDTO dto){
        dto.setIdProduto(null);
        validaProduto(dto);
        Produto obj = new Produto(dto);
        return produtoRepo.save(obj);
    }
    private void validaProduto(ProdutoDTO dto){
        Optional<Produto> obj = produtoRepo.findByCodigoBarra(dto.getCodigoBarra());
        if(obj.isPresent() && obj.get().getIdProduto() != dto.getIdProduto()){
            throw new DataIntegrityViolationException("Código de barras já cadastrado!");
        }
        Optional<GrupoProduto> grupoProduto = grupoProdutoRepo.findById(dto.getGrupoProduto());
        if(!grupoProduto.isPresent()){
            throw new DataIntegrityViolationException("Grupo de Produto - "+ dto.getGrupoProduto()+ " não está cadastrado!");
        }

    }
    public Produto update(Long id, ProdutoDTO objDto){
        objDto.setIdProduto(id);
        Produto oldObj = findById(id);
        validaProduto(objDto);
        oldObj = new Produto(objDto);
        return produtoRepo.save(oldObj);
    }
    public void delete(Long id){
        Produto obj = findById(id);
        produtoRepo.deleteById(id);
    }
}
