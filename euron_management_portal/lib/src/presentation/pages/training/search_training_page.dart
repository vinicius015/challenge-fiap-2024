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
    Training(name: 'Treinamento A', code: "11111", sector: 'Produção', quiz: [
      {
        'question': 'Qual é o objetivo do treinamento A?',
        'answers': [
          {'answer': 'Resposta 1', 'isCorrect': false},
          {'answer': 'Resposta 2', 'isCorrect': true},
          {'answer': 'Resposta 3', 'isCorrect': false},
          {'answer': 'Resposta 4', 'isCorrect': false},
          {'answer': 'Resposta 5', 'isCorrect': false},
        ]
      },
      {
        'question': 'Qual é o procedimento correto?',
        'answers': [
          {'answer': 'Resposta A', 'isCorrect': true},
          {'answer': 'Resposta B', 'isCorrect': false},
          {'answer': 'Resposta C', 'isCorrect': false},
          {'answer': 'Resposta D', 'isCorrect': false},
          {'answer': 'Resposta E', 'isCorrect': false},
        ]
      }
    ]),
    Training(name: 'Treinamento B', code: "11111", sector: 'Qualidade', quiz: [
      {
        'question': 'Qual é o objetivo do treinamento B?',
        'answers': [
          {'answer': 'Resposta 1', 'isCorrect': false},
          {'answer': 'Resposta 2', 'isCorrect': true},
          {'answer': 'Resposta 3', 'isCorrect': false},
          {'answer': 'Resposta 4', 'isCorrect': false},
          {'answer': 'Resposta 5', 'isCorrect': false},
        ]
      },
      {
        'question': 'Qual é o procedimento correto?',
        'answers': [
          {'answer': 'Resposta A', 'isCorrect': false},
          {'answer': 'Resposta B', 'isCorrect': false},
          {'answer': 'Resposta C', 'isCorrect': false},
          {'answer': 'Resposta D', 'isCorrect': false},
          {'answer': 'Resposta E', 'isCorrect': true},
        ]
      }
    ]),
    Training(
      name: 'Treinamento C',
      code: "11111",
      sector: 'Manutenção',
      quiz: [
        {
          'question': 'Qual é o objetivo do treinamento C?',
          'answers': [
            {'answer': 'Resposta 1', 'isCorrect': false},
            {'answer': 'Resposta 2', 'isCorrect': false},
            {'answer': 'Resposta 3', 'isCorrect': false},
            {'answer': 'Resposta 4', 'isCorrect': true},
            {'answer': 'Resposta 5', 'isCorrect': false},
          ]
        },
        {
          'question': 'Qual é o procedimento correto?',
          'answers': [
            {'answer': 'Resposta A', 'isCorrect': false},
            {'answer': 'Resposta B', 'isCorrect': false},
            {'answer': 'Resposta C', 'isCorrect': false},
            {'answer': 'Resposta D', 'isCorrect': true},
            {'answer': 'Resposta E', 'isCorrect': false},
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
