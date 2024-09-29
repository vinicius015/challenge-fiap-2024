import 'package:flutter/material.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';

class SearchSigningsPage extends StatefulWidget {
  const SearchSigningsPage({super.key});

  @override
  State<SearchSigningsPage> createState() => _SearchSigningsPageState();
}

class _SearchSigningsPageState extends State<SearchSigningsPage> {
  final TextEditingController _searchController = TextEditingController();
  bool _selectAll = false; // Controla a seleção de todos os itens

  // Lista de treinos para o dropdown, incluindo a opção "Todos"
  final List<String> _trainings = ['Todos', 'Manipulação Segura de Substâncias Químicas', 'Boas Práticas de Fabricação (BPF)', 'Operação de Máquinas e Equipamentos'];
  String? _selectedTraining; // Treinamento selecionado no dropdown

  // Exemplo de dados de assinatura
  final List<Signing> _signings = [
    Signing(id: 1, employee: 'Gabriel Penna', training: 'Manipulação Segura de Substâncias Químicas', isSigned: true),
    Signing(id: 2, employee: 'Vinicios Romano', training: 'Boas Práticas de Fabricação (BPF)', isSigned: false),
    Signing(id: 3, employee: 'Vinicios Romano', training: 'Operação de Máquinas e Equipamentos', isSigned: true),
  ];

  // Controla a seleção de itens individuais
  late List<bool> _selectedItems;

  // Inicializa a lista de seleção de itens
  @override
  void initState() {
    super.initState();
    _selectedItems = List<bool>.generate(_signings.length, (index) => false);
  }

  // Método para filtrar os itens
  List<Signing> _filteredSignings() {
    List<Signing> filteredList = _signings;

    // Filtra pela barra de pesquisa
    if (_searchController.text.isNotEmpty) {
      filteredList = filteredList.where((signing) {
        return signing.employee.toLowerCase().contains(_searchController.text.toLowerCase()) ||
               signing.training.toLowerCase().contains(_searchController.text.toLowerCase());
      }).toList();
    }

    // Filtra pelo treinamento selecionado
    if (_selectedTraining != null && _selectedTraining != 'Todos') {
      filteredList = filteredList.where((signing) => signing.training == _selectedTraining).toList();
    }

    return filteredList;
  }

  // Método para simular o download do documento assinado
  void _download(Signing signing) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Baixando documento para ${signing.employee}...')),
    );
  }

  // Método para baixar todos os selecionados
  void _downloadSelected() {
    List<Signing> selectedSignings = [];
    for (int i = 0; i < _selectedItems.length; i++) {
      if (_selectedItems[i]) {
        selectedSignings.add(_filteredSignings()[i]);
      }
    }

    if (selectedSignings.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Nenhum item selecionado para baixar.')),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Baixando documentos: ${selectedSignings.map((s) => s.employee).join(', ')}')),
      );
      // Aqui você pode implementar a lógica para baixar os documentos dos assinantes
    }
  }

  // Atualiza o estado de seleção de todos os itens
  void _updateSelectAll(bool? value) {
    setState(() {
      _selectAll = value!;
      for (int i = 0; i < _selectedItems.length; i++) {
        _selectedItems[i] = _selectAll;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          children: [
            // Barra de pesquisa com filtro
            TextField(
              controller: _searchController,
              decoration: InputDecoration(
                labelText: 'Buscar Funcionário',
                border: OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: const Icon(Icons.search),
                  onPressed: () {
                    setState(() {}); // Atualiza a lista ao pesquisar
                  },
                ),
              ),
            ),
            const SizedBox(height: 20),

            // Dropdown para selecionar o treinamento
            DropdownButtonFormField<String>(
              value: _selectedTraining,
              hint: const Text('Filtrar por Treinamento'),
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
            ),
            const SizedBox(height: 20),

            // Checkbox para selecionar todos os itens e botão de download
            Row(
              children: [
                Checkbox(
                  value: _selectAll,
                  onChanged: _updateSelectAll,
                ),
                const Text('Selecionar Todos'),
                const Spacer(),
                IconButton(
                  icon: const Icon(Icons.download),
                  onPressed: _downloadSelected,
                  tooltip: 'Baixar Todos Selecionados',
                ),
              ],
            ),
            const SizedBox(height: 20),

            // Lista de itens de assinatura
            Expanded(
              child: ListView.builder(
                itemCount: _filteredSignings().length,
                itemBuilder: (context, index) {
                  final signing = _filteredSignings()[index];
                  return Card(
                    child: ListTile(
                      leading: Checkbox(
                        value: _selectedItems[index],
                        onChanged: (bool? value) {
                          setState(() {
                            _selectedItems[index] = value!;
                            // Atualiza o estado do checkbox "Selecionar Todos" se necessário
                            _selectAll = _selectedItems.every((selected) => selected);
                          });
                        },
                      ),
                      title: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text('Treinamento: ${signing.training}', style: const TextStyle(fontWeight: FontWeight.bold)),
                          Text('Funcionário: ${signing.employee}'),
                          Text('ID de Assinatura: ${signing.id}'),
                        ],
                      ),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          // Indicador de status
                          Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Icon(
                                Icons.check_circle,
                                color: signing.isSigned ? Colors.green : Colors.red,
                              ),
                              const SizedBox(height: 4),
                              Text(signing.isSigned ? 'Assinado' : 'Não Assinado', 
                                    style: TextStyle(
                                      color: signing.isSigned ? Colors.green : Colors.red,
                                      fontSize: 12,
                                    ),
                              ),
                            ],
                          ),
                          const SizedBox(width: 10),
                          // Botão de download
                          if (signing.isSigned) 
                            IconButton(
                              icon: const Icon(Icons.download),
                              onPressed: () => _download(signing),
                            ),
                        ],
                      ),
                    ),
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

// Classe de modelo para Assinatura
class Signing {
  final int id;
  final String employee;
  final String training;
  final bool isSigned;

  Signing({
    required this.id,
    required this.employee,
    required this.training,
    required this.isSigned,
  });
}
