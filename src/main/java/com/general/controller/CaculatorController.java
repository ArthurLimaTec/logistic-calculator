package com.general.controller;

import com.general.model.request.CustoPorKmRequest;
import com.general.model.request.FreteRequest;
import com.general.model.request.PesoCubadoRequest;
import com.general.model.response.CustoPorKmResponse;
import com.general.model.response.FreteResponse;
import com.general.model.response.PesoCubadoResponse;
import com.general.service.LogisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
@Tag(name = "Logística 🚚", description = "API para cálculos logísticos!")
@RequiredArgsConstructor
public class CaculatorController {

    private final LogisticService logisticService;

    @Operation(
            summary = "💰 Calcula custo mínimo por KM rodado",
            description = """
            **PARA QUEM**: Caminhoneiros autônomos e pequenas transportadoras
            
            **COMO USAR**: Informe todos os gastos mensais e KM rodados.
            A API soma tudo e divide pelos KM pra te dizer o preço MÍNIMO que você deve cobrar.
            
            **Exemplo**: Gasta R$7mil/mês, roda 5mil km → **R$1,40/km mínimo**
            
            **Dica**: Sempre cobre 20% a mais pro lucro!
            """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "✅ Cálculo realizado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustoPorKmResponse.class))),
                    @ApiResponse(responseCode = "400", description = "❌ Dados inválidos (km=0)")
            }
    )
    @PostMapping("/caminhao/custo-km")
    public ResponseEntity<CustoPorKmResponse> custoKm(@RequestBody @Valid CustoPorKmRequest request) {
        return ResponseEntity.ok(logisticService.calcularCustoKm(request));
    }

    @Operation(
            summary = "📦 Calcula frete completo viagem",
            description = """
            **PARA QUEM**: Cotação rápida pro cliente (SP→RJ 10ton)
            
            **COMO FUNCIONA**: Usa o custo/km + compara 2 métodos:
            1. **Por KM**: 450km × R$1,68 + R$250 = R$1.005
            2. **Ton-Km**: 10t × 450km × R$0,34 = R$1.530 (mais caro = vence!)
            
            **Inclui**: Taxa carga/descarga + dica ICMS 12%
            
            **Resultado**: Valor FINAL pra mandar pro cliente!
            """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "✅ Frete calculado com melhor método",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FreteResponse.class)))
            }
    )
    @PostMapping("/caminhao/frete-completo")
    public ResponseEntity<FreteResponse> freteCompleto(@RequestBody @Valid FreteRequest req) {
        return ResponseEntity.ok(logisticService.calcularFrete(req));
    }

    @Operation(
            summary = "⚖️ Calcula PESO CUBADO (TRUQUE transportadoras)",
            description = """
            **🚨 SEGREDO DAS TRANSPORTADORAS**: Cobra pelo MAIOR peso!
            
            **COMO?** Volume m³ × 300 = Peso Cubado (rodoviário)
            Ex: Caixa 2×2×2m = 8m³ × 300 = **2.400kg**
            
            **Peso real**: 500kg → Cobra pelos **2.400kg** (4,8x mais caro!)
            
            **Faturamento extra**: R$3.224 numa viagem!
            
            **Fator**: 300 rodoviário, 167 aéreo
            """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "✅ Peso para cobrar + quanto fatura extra",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PesoCubadoResponse.class)))
            }
    )
    @PostMapping("/caminhao/peso-cubado")
    public ResponseEntity<PesoCubadoResponse> pesoCubado(@RequestBody @Valid PesoCubadoRequest req) {
        return ResponseEntity.ok(logisticService.calcularPesoCubado(req));
    }
}
