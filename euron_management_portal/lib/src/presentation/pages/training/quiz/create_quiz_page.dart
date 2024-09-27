import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/bottom_nav_bar.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:euron_management_portal/src/presentation/components/decorated_text_form_field.dart';
import 'package:flutter/material.dart';

class CreateQuizPage extends StatefulWidget {
  const CreateQuizPage({super.key});

  @override
  State<CreateQuizPage> createState() => _CreateQuizPageState();
}

class _CreateQuizPageState extends State<CreateQuizPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _questionCountController =
      TextEditingController();
  final letras = ["A", "B", "C", "D", "E"];

  int _questionCount = 0;
  List<Map<String, dynamic>> _questions = [];

  List<Widget> _buildQuestionForms() {
    List<Widget> questionForms = [];
    for (int i = 0; i < _questionCount; i++) {
      questionForms.add(
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Pergunta ${i + 1}',
                style:
                    const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 15),
            DecoratedTextFormField(
              keyboardType: TextInputType.text,
              label: 'Enunciado',
              controller: _questions[i]['questionController'],
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Por favor, insira o enunciado da pergunta';
                }
                return null;
              },
            ),
            const SizedBox(height: 15),
            for (int j = 0; j < 5; j++)
              Column(
                children: [
                  Row(
                    children: [
                      Expanded(
                        child: DecoratedTextFormField(
                          keyboardType: TextInputType.text,
                          label: 'Resposta ${letras[j]}',
                          controller: _questions[i]['answerControllers'][j],
                          validator: (value) {
                            if (value == null || value.isEmpty) {
                              return 'Por favor, insira a resposta ${letras[j]}';
                            }
                            return null;
                          },
                        ),
                      ),
                      Checkbox(
                        value: _questions[i]['correctAnswerIndex'] == j,
                        onChanged: (bool? newValue) {
                          setState(() {
                            _questions[i]['correctAnswerIndex'] = newValue!
                                ? j
                                : _questions[i]['correctAnswerIndex'];
                          });
                        },
                      ),
                      const Text('Correta'),
                    ],
                  ),
                  const SizedBox(height: 15),
                ],
              ),
            const SizedBox(height: 30),
          ],
        ),
      );
    }
    return questionForms;
  }

  void _generateQuestionForms(int count) {
    _questions = List.generate(count, (index) {
      return {
        'questionController': TextEditingController(),
        'answerControllers': List.generate(5, (i) => TextEditingController()),
        'correctAnswerIndex': -1,
      };
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Form(
          key: _formKey,
          autovalidateMode: AutovalidateMode.onUserInteraction,
          child: Column(
            children: [
              DecoratedTextFormField(
                label: 'Número de Perguntas',
                controller: _questionCountController,
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Por favor, insira o número de perguntas';
                  }

                  if (int.tryParse(value) == null || int.parse(value) <= 0) {
                    return 'Por favor, insira um número válido';
                  }

                  if (int.tryParse(value)! > 10) {
                    return 'Por favor, insira um número válido';
                  }

                  return null;
                },
              ),
              const SizedBox(height: 25),
              SizedBox(
                width: double.infinity,
                height: 50,
                child: ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      setState(() {
                        _questionCount =
                            int.parse(_questionCountController.text);
                        _generateQuestionForms(_questionCount);
                      });
                    }
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: euronSoftPurple,
                    elevation: 0,
                    foregroundColor: euronWhite,
                  ),
                  child: const Text('Gerar Perguntas',
                      style: TextStyle(fontSize: 20)),
                ),
              ),
              const SizedBox(height: 25),
              Expanded(
                child: SingleChildScrollView(
                  child: Column(
                    children: _buildQuestionForms(),
                  ),
                ),
              ),
              const SizedBox(height: 25),
              SizedBox(
                width: double.infinity,
                height: 50,
                child: ElevatedButton(
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                            content: Text('Quiz criado com sucesso!')),
                      );

                      Navigator.pushReplacement(
                          context,
                          MaterialPageRoute(
                              builder: (context) =>
                                  const BottomNavBar(initialPage: 1)));
                    }
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: euronSoftPurple,
                    elevation: 0,
                    foregroundColor: euronWhite,
                  ),
                  child: const Text('Finalizar Quiz',
                      style: TextStyle(fontSize: 20)),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
