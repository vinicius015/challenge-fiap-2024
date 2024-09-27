import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/data/models/training.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:euron_management_portal/src/presentation/components/decorated_text_form_field.dart';
import 'package:euron_management_portal/src/presentation/pages/training/quiz/create_quiz_page.dart';
import 'package:flutter/material.dart';

class CreateTrainingPage extends StatefulWidget {
  final Training? training;

  const CreateTrainingPage({super.key, this.training});

  @override
  State<CreateTrainingPage> createState() => _CreateTrainingPageState();
}

class _CreateTrainingPageState extends State<CreateTrainingPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _trainingNameController = TextEditingController();
  final TextEditingController _trainingCodeController = TextEditingController();
  String? _selectedSector;

  // Lista de setores para a área farmacêutica
  final List<String> _sectors = [
    'Produção',
    'Qualidade',
    'Regulatório',
    'Pesquisa e Desenvolvimento',
    'Logística',
    'Segurança do Trabalho',
    'Assuntos Regulatórios',
    'Validação',
  ];

  @override
  void initState() {
    super.initState();
    // Se um objeto de treinamento foi passado, preencher os campos
    if (widget.training != null) {
      _trainingNameController.text = widget.training!.name;
      _trainingCodeController.text = widget.training!.code;
      _selectedSector = widget.training!.sector;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            Expanded(
              child: Center(
                child: Form(
                  key: _formKey,
                  autovalidateMode: AutovalidateMode.onUserInteraction,
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      // Nome do Treinamento
                      DecoratedTextFormField(
                        keyboardType: TextInputType.text,
                        label: 'Nome do Treinamento',
                        controller: _trainingNameController,
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Por favor, insira o nome do treinamento';
                          }
                          return null;
                        },
                      ),
                      const SizedBox(height: 25),

                      // Código do Treinamento
                      DecoratedTextFormField(
                        keyboardType: TextInputType.text,
                        label: 'Código do Treinamento',
                        controller: _trainingCodeController,
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Por favor, insira o código do treinamento';
                          }
                          return null;
                        },
                      ),
                      const SizedBox(height: 25),

                      // Dropdown para Setor
                      DropdownButtonFormField<String>(
                        decoration: InputDecoration(
                          labelText: 'Setor',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                        value: _selectedSector,
                        items: _sectors.map((String sector) {
                          return DropdownMenuItem<String>(
                            value: sector,
                            child: Text(sector),
                          );
                        }).toList(),
                        onChanged: (newValue) {
                          setState(() {
                            _selectedSector = newValue;
                          });
                        },
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Por favor, selecione o setor';
                          }
                          return null;
                        },
                      ),
                      const SizedBox(height: 25),

                      // Botão "Criar Quiz"
                      SizedBox(
                        width: double.infinity,
                        height: 50,
                        child: ElevatedButton(
                          onPressed: () {
                            if (_formKey.currentState!.validate()) {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => const CreateQuizPage(),
                                ),
                              );
                            }
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: euronSoftPurple,
                            elevation: 0,
                            foregroundColor: euronWhite,
                          ),
                          child: Text(
                            widget.training == null ?
                            'Criar Quiz' : 'Editar Quiz',
                            style: const TextStyle(fontSize: 20),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
            Image.asset(
              'assets/eurofarma_logo.png',
              width: 200,
            ),
          ],
        ),
      ),
    );
  }
}
