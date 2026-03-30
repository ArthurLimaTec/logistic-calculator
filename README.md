# 🚚 **LogísticaPro API** - Calculadora Completa para Transportadoras & Indústrias

**API** com **7 cálculos essenciais** que **transportadoras, fábricas e lojas** usam todo dia!

## ✨ **O que faz?**
```
✅ CUSTO/KM → "Cobre R$1,68/km sem prejuízo"
✅ PESO CUBADO → "Fature 5x mais com caixas grandes!"
✅ FRETE COMPLETO → "SP→RJ 10t = R$1.436"
✅ ANTT OFICIAL → "Nunca mais multa R$550!"
✅ EOQ Estoque → "Compre 1.250 und/pedido"
✅ EPQ Produção → "Produza 2.800 pares/lote"
✅ Frete Integrado → "Cotação FINAL pro cliente"
```

## 🚀 **Endpoints Disponíveis**

| Endpoint | Descrição | Exemplo |
|----------|-----------|---------|
| `POST /custo-km` | Custo mínimo por km rodado | `R$1.40/km → cobre R$1.68` |
| `POST /peso-cubado` | **TRUQUE**: Peso real vs cubado | `500kg real → cobra 2.400kg!` |
| `POST /frete-completo` | Cotação viagem | `SP→RJ 10t = R$1.436 c/ ICMS` |
| `POST /piso-antt` | **Lei 13.703** frete mínimo | `4 eixos 400km = R$2.252` |
| `POST /estoque-otimo` | **EOQ** lote ideal compra | `1.250 und/pedido` |
| `POST /epq-producao` | **EPQ** lote ideal fábrica | `2.800 pares/lote` |
| `POST /frete-integrado` | **TUDO** em 1 cálculo | `Cotação pronta pro WhatsApp` |

## 💰 **Para Quem?**
- **Caminhoneiros autônomos** → Cotação sem prejuízo
- **Pequenas transportadoras** → Evita multa ANTT
- **Lojas/PMEs** → Estoque sem empacar
- **Fábricas Franca-SP** → Produção otimizada

## 🛠 **Tech Stack**
```
Spring Boot 3.3.0 • Java 21 • Springdoc OpenAPI (Swagger)
Maven • JSON Config ANTT • 100% RESTful
```