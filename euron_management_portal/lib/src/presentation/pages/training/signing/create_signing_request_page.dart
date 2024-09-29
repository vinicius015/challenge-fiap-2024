import 'package:flutter/material.dart';
import 'package:euron_management_portal/src/config/globals.dart';
import 'package:euron_management_portal/src/presentation/components/bottom_nav_bar.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:multi_select_flutter/dialog/multi_select_dialog_field.dart';
import 'package:multi_select_flutter/util/multi_select_item.dart';

class CreateSigningRequestPage extends StatefulWidget {
  const CreateSigningRequestPage({super.key});

  @override
  State<CreateSigningRequestPage> createState() =>
      _CreateSigningRequestPageState();
}

class _CreateSigningRequestPageState extends State<CreateSigningRequestPage> {
  final _formKey = GlobalKey<FormState>();
  DateTime? _selectedDate;
  String? _selectedTraining;
  String? _selectedEmployee;
  List<String> _selectedEmployees = [];

  bool _isIndividual = true; // Controla se o tipo é Individual ou Grupo

  // Exemplo de lista de treinamentos
  final List<String> _trainings = [
    'Manipulação de Substâncias Químicas',
    'Boas Práticas de Fabricação (BPF)',
    'Operação de Máquinas',
    'Testes'
  ];

  // Exemplo de lista de funcionários
  final List<String> _employees = [
    'Gabriel Penna de Lima',
    'Vinicios Romano',
  ];

  void _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: _selectedDate ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != _selectedDate) {
      setState(() {
        _selectedDate = picked;
      });
    }
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
          child: SingleChildScrollView(
            child: Column(
              children: [
                // Dropdown para Treinamento
                DropdownButtonFormField<String>(
                  decoration: const InputDecoration(
                    labelText: 'Treinamento',
                    border: OutlineInputBorder(),
                  ),
                  value: _selectedTraining,
                  items: _trainings.map((String training) {
                    return DropdownMenuItem<String>(
                      value: training,
                      child: Text(training),
                    );
                  }).toList(),
                  onChanged: (String? newValue) {
                    setState(() {
                      _selectedTraining = newValue;
                    });
                  },
                  validator: (value) {
                    if (value == null) {
                      return 'Por favor, selecione um treinamento';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 20),
            
                // DatePicker para Data de Realização
                TextFormField(
                  readOnly: true,
                  decoration: InputDecoration(
                    labelText: 'Data de Realização',
                    border: const OutlineInputBorder(),
                    suffixIcon: IconButton(
                      icon: const Icon(Icons.calendar_today),
                      onPressed: () => _selectDate(context),
                    ),
                  ),
                  controller: TextEditingController(
                      text: _selectedDate != null
                          ? "${_selectedDate!.toLocal()}".split(' ')[0]
                          : ""),
                  validator: (value) {
                    if (_selectedDate == null) {
                      return 'Por favor, selecione uma data';
                    }
                    return null;
                  },
                ),
                const SizedBox(height: 20),
            
                // Radio Buttons para Individual ou Grupo
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        Radio<bool>(
                          value: true,
                          groupValue: _isIndividual,
                          onChanged: (bool? value) {
                            setState(() {
                              _isIndividual = value!;
                            });
                          },
                        ),
                        const Text('Individual'),
                      ],
                    ),
                    Row(
                      children: [
                        Radio<bool>(
                          value: false,
                          groupValue: _isIndividual,
                          onChanged: (bool? value) {
                            setState(() {
                              _isIndividual = value!;
                            });
                          },
                        ),
                        const Text('Grupo'),
                      ],
                    ),
                  ],
                ),
                const SizedBox(height: 20),
            
                // Dropdown ou Multi-Select baseado na escolha
                if (_isIndividual) ...[
                  // Campo Dropdown para Funcionário Individual
                  DropdownButtonFormField<String>(
                    decoration: const InputDecoration(
                      labelText: 'Funcionário',
                      border: OutlineInputBorder(),
                    ),
                    value: _selectedEmployee,
                    items: _employees.map((String employee) {
                      return DropdownMenuItem<String>(
                        value: employee,
                        child: Text(employee),
                      );
                    }).toList(),
                    onChanged: (String? newValue) {
                      setState(() {
                        _selectedEmployee = newValue;
                      });
                    },
                    validator: (value) {
                      if (value == null) {
                        return 'Por favor, selecione um funcionário';
                      }
                      return null;
                    },
                  ),
                ] else ...[
                  // Campo Multi-Select para Funcionários em Grupo
                  MultiSelectDialogField(
                    items: _employees
                        .map((employee) => MultiSelectItem(employee, employee))
                        .toList(),
                    title: const Text("Funcionários"),
                    searchable: true,
                    onConfirm: (values) {
                      setState(() {
                        _selectedEmployees = values.cast<String>();
                      });
                    },
                  ),
                ],
                const SizedBox(height: 20),
            
                // Botão para finalizar
                SizedBox(
                  width: double.infinity,
                  height: 50,
                  child: ElevatedButton(
                    onPressed: () {
                      if (_formKey.currentState!.validate()) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(
                              content: Text('Solicitação criada com sucesso!')),
                        );
            
                        Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) => const BottomNavBar(
                                    isAdmin: true, initialPage: 1)));
                      }
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: euronSoftPurple,
                      elevation: 0,
                      foregroundColor: euronWhite,
                    ),
                    child: const Text('Finalizar Solicitação',
                        style: TextStyle(fontSize: 20)),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
