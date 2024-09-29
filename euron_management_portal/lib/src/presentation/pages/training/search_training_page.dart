import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/data/models/training.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:euron_management_portal/src/presentation/pages/training/create_training_page.dart';
import 'package:euron_management_portal/src/presentation/pages/training/training_details_page.dart';
import 'package:flutter/material.dart';

class SearchTrainingPage extends StatefulWidget {
  const SearchTrainingPage({super.key});

  @override
  State<SearchTrainingPage> createState() => _SearchTrainingPageState();
}

class _SearchTrainingPageState extends State<SearchTrainingPage> {
  final TextEditingController _searchController = TextEditingController();

  List<Training> trainingList = [
    Training(
        name: 'Testes',
        code: "00001",
        sector: 'Produção',
        quiz: [
          {
            'question':
                'Teste',
            'answers': [
              {'answer': 'Teste', 'isCorrect': false},
              {'answer': 'Teste', 'isCorrect': false},
              {'answer': 'Teste', 'isCorrect': true},
              {'answer': 'Teste', 'isCorrect': false},
              {'answer': 'Teste', 'isCorrect': false},
            ]
          }
        ]),
    Training(
        name: 'Manipulação Segura de Substâncias Químicas',
        code: "00001",
        sector: 'Produção',
        quiz: [
          {
            'question':
                'Qual é o equipamento essencial ao manusear produtos químicos?',
            'answers': [
              {'answer': 'Capacete', 'isCorrect': false},
              {'answer': 'Avental', 'isCorrect': false},
              {'answer': 'EPI', 'isCorrect': true},
              {'answer': 'Luvas', 'isCorrect': false},
              {'answer': 'Óculos', 'isCorrect': false},
            ]
          },
          {
            'question':
                'Qual é a prática correta ao armazenar produtos perigosos?',
            'answers': [
              {'answer': 'Jogar', 'isCorrect': false},
              {'answer': 'Isolar', 'isCorrect': false},
              {'answer': 'Misturar', 'isCorrect': false},
              {'answer': 'Separar', 'isCorrect': false},
              {'answer': 'Classificar', 'isCorrect': true},
            ]
          },
          {
            'question': 'O que fazer em caso de vazamento químico?',
            'answers': [
              {'answer': 'Ignorar', 'isCorrect': false},
              {'answer': 'Limpar', 'isCorrect': false},
              {'answer': 'Reportar', 'isCorrect': true},
              {'answer': 'Sair', 'isCorrect': false},
              {'answer': 'Recolher', 'isCorrect': false},
            ]
          },
        ]),
    Training(
        name: 'Boas Práticas de Fabricação (BPF)',
        code: "00002",
        sector: 'Qualidade',
        quiz: [
          {
            'question': 'O que garante a qualidade dos produtos farmacêuticos?',
            'answers': [
              {'answer': 'BPF', 'isCorrect': true},
              {'answer': 'CNPJ', 'isCorrect': false},
              {'answer': 'CRM', 'isCorrect': false},
              {'answer': 'ISO', 'isCorrect': false},
              {'answer': 'OHSAS', 'isCorrect': false},
            ]
          },
          {
            'question': 'Qual órgão regula as BPF no Brasil?',
            'answers': [
              {'answer': 'FDA', 'isCorrect': false},
              {'answer': 'ANVISA', 'isCorrect': true},
              {'answer': 'EMA', 'isCorrect': false},
              {'answer': 'WHO', 'isCorrect': false},
              {'answer': 'USP', 'isCorrect': false},
            ]
          },
          {
            'question': 'O que deve ser sempre registrado durante a produção?',
            'answers': [
              {'answer': 'Estoque', 'isCorrect': false},
              {'answer': 'Processo', 'isCorrect': false},
              {'answer': 'Horas', 'isCorrect': false},
              {'answer': 'Desperdício', 'isCorrect': false},
              {'answer': 'Documentação', 'isCorrect': true},
            ]
          }
        ]),
    Training(
      name: 'Operação de Máquinas e Equipamentos',
      code: "00003",
      sector: 'Manutenção',
      quiz: [
        {
          'question': 'Qual é o tipo de manutenção que evita falhas?',
          'answers': [
            {'answer': 'Emergencial', 'isCorrect': false},
            {'answer': 'Preditiva', 'isCorrect': false},
            {'answer': 'Corretiva', 'isCorrect': false},
            {'answer': 'Preventiva', 'isCorrect': true},
            {'answer': 'Passiva', 'isCorrect': false},
          ]
        },
        {
          'question': 'O que deve ser verificado antes de iniciar uma máquina?',
          'answers': [
            {'answer': 'Motor', 'isCorrect': false},
            {'answer': 'Lubrificação', 'isCorrect': true},
            {'answer': 'Energia', 'isCorrect': false},
            {'answer': 'Ruído', 'isCorrect': false},
            {'answer': 'Sensor', 'isCorrect': false},
          ]
        },
        {
          'question': 'Quem pode operar uma máquina de produção?',
          'answers': [
            {'answer': 'Visitante', 'isCorrect': false},
            {'answer': 'Gerente', 'isCorrect': false},
            {'answer': 'Operador', 'isCorrect': true},
            {'answer': 'Técnico', 'isCorrect': false},
            {'answer': 'Qualquer um', 'isCorrect': false},
          ]
        }
      ],
    )
  ];

  String _filter = '';

  @override
  Widget build(BuildContext context) {
    List<Training> filteredTrainings = trainingList
        .where((training) =>
            training.name.toLowerCase().contains(_filter.toLowerCase()))
        .toList();

    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            TextField(
              controller: _searchController,
              onChanged: (value) {
                setState(() {
                  _filter = value;
                });
              },
              decoration: InputDecoration(
                labelText: 'Buscar Treinamento',
                border: const OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: const Icon(Icons.clear),
                  onPressed: () {
                    _searchController.clear();
                    setState(() {
                      _filter = '';
                    });
                  },
                ),
              ),
            ),
            const SizedBox(height: 20),
            Expanded(
              child: ListView.builder(
                itemCount: filteredTrainings.length,
                itemBuilder: (context, index) {
                  return ListTile(
                    title: Text(filteredTrainings[index].name),
                    subtitle: Text(filteredTrainings[index].sector),
                    trailing: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        IconButton(
                          icon: const Icon(Icons.edit),
                          color: euronSoftPurple,
                          onPressed: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => CreateTrainingPage(
                                        training: trainingList[index])));
                          },
                        ),
                        IconButton(
                          icon: const Icon(Icons.delete),
                          color: euronSoftPurple,
                          onPressed: () {
                            // Lógica para deletar o treinamento
                            setState(() {
                              trainingList.removeAt(index);
                            });
                          },
                        ),
                      ],
                    ),
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => TrainingDetailsPage(
                            training: trainingList[index],
                          ),
                        ),
                      );
                    },
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
