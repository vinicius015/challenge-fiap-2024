import 'package:euron_management_portal/src/presentation/pages/training/signing/signing_quiz_page.dart';
import 'package:flutter/material.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';

class SearchSigningRequestsPage extends StatefulWidget {
  const SearchSigningRequestsPage({super.key});

  @override
  State<SearchSigningRequestsPage> createState() => _SearchSigningRequestsPageState();
}

class _SearchSigningRequestsPageState extends State<SearchSigningRequestsPage> {
  final TextEditingController _searchController = TextEditingController();
  bool _selectAll = false; // Controla a seleção de todos os itens

  // Lista de treinos para o dropdown, incluindo a opção "Todos"
  final List<String> _trainings = ['Todos', 'Limpeza de Laboratório', 'Manuseio de Tupla', 'Químicos Gerais'];
  String? _selectedTraining; // Treinamento selecionado no dropdown

  // Exemplo de dados de solicitação de assinatura
  final List<SigningRequest> _signingRequests = [
    SigningRequest(id: 1, training: 'Limpeza de Laboratório', isSigned: true),
    SigningRequest(id: 2, training: 'Manuseio de Tupla', isSigned: false),
    SigningRequest(id: 3, training: 'Químicos Gerais', isSigned: true),
  ];

  // Controla a seleção de itens individuais
  late List<bool> _selectedItems;

  // Inicializa a lista de seleção de itens
  @override
  void initState() {
    super.initState();
    _selectedItems = List<bool>.generate(_signingRequests.length, (index) => false);
  }

  // Método para filtrar os itens
  List<SigningRequest> _filteredSigningRequests() {
    List<SigningRequest> filteredList = _signingRequests;

    // Filtra pelo treinamento selecionado
    if (_selectedTraining != null && _selectedTraining != 'Todos') {
      filteredList = filteredList.where((request) => request.training == _selectedTraining).toList();
    }

    return filteredList;
  }

  // Método para simular o download do documento assinado
  void _download(SigningRequest request) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Baixando documento para a solicitação do treinamento ${request.training}...')),
    );
  }

  // Método para baixar todos os selecionados
  void _downloadSelected() {
    List<SigningRequest> selectedRequests = [];
    for (int i = 0; i < _selectedItems.length; i++) {
      if (_selectedItems[i]) {
        selectedRequests.add(_filteredSigningRequests()[i]);
      }
    }

    if (selectedRequests.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Nenhum item selecionado para baixar.')),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Baixando documentos: ${selectedRequests.map((r) => r.training).join(', ')}')),
      );
      // Aqui você pode implementar a lógica para baixar os documentos das solicitações
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

            // Lista de itens de solicitação de assinatura
            Expanded(
              child: ListView.builder(
                itemCount: _filteredSigningRequests().length,
                itemBuilder: (context, index) {
                  final request = _filteredSigningRequests()[index];
                  return Card(
                    child: ListTile(
                      onTap: () {
                        Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      const QuizPage()));
                      },
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
                          Text('Treinamento: ${request.training}', style: const TextStyle(fontWeight: FontWeight.bold)),
                          Text('ID de Assinatura: ${request.id}'),
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
                                color: request.isSigned ? Colors.green : Colors.red,
                              ),
                              const SizedBox(height: 4),
                              Text(request.isSigned ? 'Assinado' : 'Pendente', 
                                    style: TextStyle(
                                      color: request.isSigned ? Colors.green : Colors.red,
                                      fontSize: 12,
                                    ),
                              ),
                            ],
                          ),
                          const SizedBox(width: 10),
                          // Botão de download
                          if (request.isSigned) 
                            IconButton(
                              icon: const Icon(Icons.download),
                              onPressed: () => _download(request),
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

// Classe de modelo para Solicitação de Assinatura
class SigningRequest {
  final int id;
  final String training;
  final bool isSigned;

  SigningRequest({
    required this.id,
    required this.training,
    required this.isSigned,
  });
}
